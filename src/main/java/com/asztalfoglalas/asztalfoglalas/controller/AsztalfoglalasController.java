package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.service.AsztalService;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import com.asztalfoglalas.asztalfoglalas.service.FoglalasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("foglalas", new FoglalasDTO());
        return "index";
    }

    @GetMapping("/sikeres-foglalas")
    public String showSikeresFoglalas(Model model) {
        model.addAttribute("foglalas", new FoglalasDTO());
        return "sikeres-foglalas";
    }

    @PostMapping("/asztalfoglalas")
    public String asztalfoglalas(@ModelAttribute(name = "foglalas") FoglalasDTO foglalas, Principal principal, Model model) {

        model.addAttribute("foglalas", foglalas);
        foglalasService.save(foglalas, principal);
        return "redirect:/sikeres-foglalas";
    }

    @PostMapping("/idopontfoglalas")
    public String saveReszletek(@ModelAttribute(name = "foglalas") FoglalasDTO foglalas, Model model) {
        LocalDateTime most = LocalDateTime.now();

        LocalDateTime foglalasVege = foglalas.getIdopont().plusMinutes(foglalas.getMeddig());
        List<Asztal> foglaltAsztalok = asztalService.getFoglaltAsztalok(foglalas.getIdopont(), foglalasVege);
        List<Asztal> szabadAsztalok = asztalService.getSzabadAsztalok(foglalas.getIdopont(), foglalasVege);
        List<Asztal> asztalok = asztalService.findAll();

        model.addAttribute("foglaltAsztalok", foglaltAsztalok);
        model.addAttribute("szabadAsztalok", szabadAsztalok);
        model.addAttribute("asztalok", asztalok);
        model.addAttribute("foglalas", foglalas);

        if(foglalas.getIdopont().isBefore(most)) {
            System.out.println("Lejárt dátum!");
            return "index";
        }

        return "ulohely-foglalas";
    }

}
