package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import com.asztalfoglalas.asztalfoglalas.service.AsztalService;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import com.asztalfoglalas.asztalfoglalas.service.FoglalasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class AsztalfoglalasController {

    AsztalService asztalService;
    FelhasznaloService felhasznaloService;
    FoglalasService foglalasService;

    @Autowired
    public AsztalfoglalasController(FelhasznaloService felhasznaloService, FoglalasService foglalasService, AsztalService asztalService) {
        this.felhasznaloService = felhasznaloService;
        this.foglalasService = foglalasService;
        this.asztalService = asztalService;
    }

    @GetMapping("/idopontvalasztas")
    public String idopontfoglalas(Model model, Principal principal) {
        FoglalasDTO foglalas = new FoglalasDTO();
        int felhasznaloId = felhasznaloService.findFelhasznaloIdByEmail(principal.getName());
        foglalas.setFelhasznaloId(felhasznaloId);
        //Thymeleaf számára, hogy a th:field-ben már legyen kezdeti érték, a vendégszám és a foglalás minimális hosszát
        //beállítom itt a modellben, amelyet a felhasználó felülírhat majd
        foglalas.setVendegek(1);
        foglalas.setMeddig(30);
        model.addAttribute("foglalas", foglalas);
        return "idopontfoglalas";
    }

    @GetMapping("/sikeres-foglalas")
    public String showSikeresFoglalas(@ModelAttribute(name = "foglalas") Foglalas foglalas) {

        if(foglalas == null || foglalas.getFelhasznalo() == null) {
            return "redirect:/";
        }
        return "sikeres-foglalas";
    }

    @PostMapping("/asztalfoglalas")
    public String asztalfoglalas(@ModelAttribute(name = "foglalas") FoglalasDTO foglalas, Model model,
                                 RedirectAttributes redirectAttributes) {

        List<Asztal> foglaltAsztalok = asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
        List<Asztal> szabadAsztalok = asztalService.getSzabadAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
        List<Asztal> asztalok = asztalService.findAll();

        try {
            Foglalas ujFoglalas = foglalasService.save(foglalas);
            redirectAttributes.addFlashAttribute("foglalas", ujFoglalas);
            return "redirect:/sikeres-foglalas";

        } catch (RuntimeException e) {
            addCommonDataToModel(model, foglaltAsztalok, szabadAsztalok, asztalok, foglalas);
            model.addAttribute("hiba", e.getMessage());

            return "ulohely-foglalas";
        }
    }

    @PostMapping("/idopontfoglalas")
    public String saveReszletek(@Valid @ModelAttribute(name = "foglalas") FoglalasDTO foglalas,
                                BindingResult bindingResult, Model model) {

        LocalDateTime most = LocalDateTime.now().plusMinutes(60);

        //foglalás adatainak ellenőrzése
        if (bindingResult.hasErrors()){
            return "idopontfoglalas";
        }

        //felhasználó kiszűrése hogy az adott időközben ne legyen már aktív foglalása
        if(foglalasService.checkAktivFoglalasByFelhasznaloId(foglalas.getFelhasznaloId(),
                foglalas.getIdopont(), foglalas.getFoglalasVege())) {
            model.addAttribute("hiba", "Már foglaltál erre az időpontra!");
            return "idopontfoglalas";
        }

        List<Asztal> foglaltAsztalok = asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
        List<Asztal> szabadAsztalok = asztalService.getSzabadAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
        List<Asztal> asztalok = asztalService.findAll();

        // ha az összes asztal foglalt a foglalás időpontjában, akkor visszadobja a felhasználót
        if(foglaltAsztalok.size() == asztalok.size()) {
            model.addAttribute("hiba", "Ebben az időpontban már minden asztal foglalt!");
            return "idopontfoglalas";
        }

        addCommonDataToModel(model, foglaltAsztalok, szabadAsztalok, asztalok, foglalas);

        if(foglalas.getIdopont().isBefore(most)) {
            model.addAttribute("hiba", "Legalább 60 perccel későbbi időpontot válassz!");
            return "idopontfoglalas";
        }

        return "ulohely-foglalas";
    }

    @GetMapping("/aktiv-foglalasaim")
    public String showAktivFoglalasok(Principal principal, Model model) {

        Felhasznalo felhasznalo = felhasznaloService.findFelhasznaloByEmail(principal.getName());
        felhasznalo.setFoglalasok(foglalasService.getAktivFoglalasokByFelhasznaloId(felhasznalo.getId()));
        model.addAttribute("felhasznalo", felhasznalo);

        return "aktiv-foglalasok";
    }

    @PostMapping("/foglalas-lemondas/{foglalasId}")
    public String foglalasLemondasa(@PathVariable int foglalasId, Principal principal,
                                    RedirectAttributes redirectAttributes) {

        Felhasznalo felhasznalo = felhasznaloService.findFelhasznaloByEmail(principal.getName());
        Foglalas foglalas = foglalasService.findById(foglalasId);

        if(foglalas == null || felhasznalo.getId() != foglalas.getFelhasznalo().getId()) {
            redirectAttributes.addFlashAttribute("hiba",
                    "A törölni kívánt foglalás nem elérhető!");
            return "redirect:/aktiv-foglalasaim";
        }

        foglalasService.deleteById(foglalasId);
        redirectAttributes.addFlashAttribute("sikeresTorles", "A foglalás sikeresen törölve!");
        return "redirect:/aktiv-foglalasaim";
    }

    private void addCommonDataToModel(Model model, List<Asztal> foglaltAsztalok, List<Asztal> szabadAsztalok,
                                      List<Asztal> asztalok, FoglalasDTO foglalas) {
        model.addAttribute("foglaltAsztalok", foglaltAsztalok);
        model.addAttribute("szabadAsztalok", szabadAsztalok);
        model.addAttribute("asztalok", asztalok);
        model.addAttribute("foglalas", foglalas);
    }

}
