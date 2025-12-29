package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import com.asztalfoglalas.asztalfoglalas.service.AsztalService;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import com.asztalfoglalas.asztalfoglalas.service.FoglalasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String idopontfoglalas(Model model) {
        model.addAttribute("foglalas", new FoglalasDTO());
        return "idopontfoglalas";
    }

    @GetMapping("/sikeres-foglalas")
    public String showSikeresFoglalas(@ModelAttribute(name = "foglalas") Foglalas foglalas, Model model) {

        if(foglalas == null || foglalas.getFelhasznalo() == null) {
            return "redirect:/";
        }
        return "sikeres-foglalas";
    }

    @PostMapping("/asztalfoglalas")
    public String asztalfoglalas(@ModelAttribute(name = "foglalas") FoglalasDTO foglalas, Model model,
                                 RedirectAttributes redirectAttributes) {

        Asztal asztal = asztalService.findById(foglalas.getAsztalId());

        //asztal kiszűrése id alapján, hogy az adott időközben ne legyen már lefoglalva
        if(asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege()).contains(asztal)) {
            List<Asztal> foglaltAsztalok = asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
            List<Asztal> szabadAsztalok = asztalService.getSzabadAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
            List<Asztal> asztalok = asztalService.findAll();


            model.addAttribute("hiba", "Ez az asztal már foglalt a kiválasztott időben!");
            model.addAttribute("foglaltAsztalok", foglaltAsztalok);
            model.addAttribute("szabadAsztalok", szabadAsztalok);
            model.addAttribute("asztalok", asztalok);
            model.addAttribute("foglalas", foglalas);
            return "ulohely-foglalas";
        }

        //vendégek száma ne haladja túl a kiválasztott asztal férőhelyét
        if(foglalas.getVendegek() > asztal.getFerohely()) {
            List<Asztal> foglaltAsztalok = asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
            List<Asztal> szabadAsztalok = asztalService.getSzabadAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
            List<Asztal> asztalok = asztalService.findAll();

            model.addAttribute("hiba", "A kiválasztott asztal túl kicsi a vendégeid számához képest!");
            model.addAttribute("foglaltAsztalok", foglaltAsztalok);
            model.addAttribute("szabadAsztalok", szabadAsztalok);
            model.addAttribute("asztalok", asztalok);
            model.addAttribute("foglalas", foglalas);
            return "ulohely-foglalas";
        }

        //ha a vendégek száma jóval kisebb (pl. <2) mint az asztal férőhelye, csak akkor fogadja el a foglalást,
        // ha nincs szabad asztal kevesebb férőhellyel (a vendégek számánál nem kevesebbel) az időpontban.
        if(((asztal.getFerohely() - foglalas.getVendegek()) > 2)) {

            List<Asztal> foglaltAsztalok = asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
            List<Asztal> szabadAsztalok = asztalService.getSzabadAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
            List<Asztal> asztalok = asztalService.findAll();

            if(szabadAsztalok.stream().filter(a -> (a.getFerohely() - foglalas.getVendegek()) < 2).count() > 0 ) {
                model.addAttribute("hiba", "A kiválasztott asztal túl nagy férőhellyel rendelkezik a vendégeid számához képest!");
                model.addAttribute("foglaltAsztalok", foglaltAsztalok);
                model.addAttribute("szabadAsztalok", szabadAsztalok);
                model.addAttribute("asztalok", asztalok);
                model.addAttribute("foglalas", foglalas);
                return "ulohely-foglalas";
            }
        }

        foglalasService.save(foglalas);
        Foglalas ujFoglalas = foglalasService.findLatestFoglalasByFelhasznaloId(foglalas.getFelhasznaloId());
        redirectAttributes.addFlashAttribute("foglalas", ujFoglalas);
        return "redirect:/sikeres-foglalas";
    }

    @PostMapping("/idopontfoglalas")
    public String saveReszletek(@ModelAttribute(name = "foglalas") FoglalasDTO foglalas,
                                Principal principal, Model model) {

        LocalDateTime most = LocalDateTime.now().plusMinutes(60);
        int felhasznaloId = felhasznaloService.findFelhasznaloIdByEmail(principal.getName());
        foglalas.setFelhasznaloId(felhasznaloId);


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

        model.addAttribute("foglaltAsztalok", foglaltAsztalok);
        model.addAttribute("szabadAsztalok", szabadAsztalok);
        model.addAttribute("asztalok", asztalok);
        model.addAttribute("foglalas", foglalas);

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
    public String foglalasLemondasa(@PathVariable int foglalasId, Principal principal, Model model,
                                    RedirectAttributes redirectAttributes) {

        Felhasznalo felhasznalo = felhasznaloService.findFelhasznaloByEmail(principal.getName());

        if(foglalasService.findById(foglalasId) == null ||
                felhasznalo.getId() != foglalasService.findById(foglalasId).getFelhasznalo().getId()) {

            redirectAttributes.addFlashAttribute("hiba",
                    "A törölni kívánt foglalás nem elérhető!");
            return "redirect:/aktiv-foglalasaim";
        }

        foglalasService.deleteById(foglalasId);
        redirectAttributes.addFlashAttribute("sikeresTorles", "A foglalás sikeresen törölve!");
        return "redirect:/aktiv-foglalasaim";
    }

}
