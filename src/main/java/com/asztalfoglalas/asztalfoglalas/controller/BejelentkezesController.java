package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BejelentkezesController {

    FelhasznaloService felhasznaloService;

    @Autowired
    public BejelentkezesController(FelhasznaloService felhasznaloService) {
        this.felhasznaloService = felhasznaloService;
    }

    @GetMapping("/bejelentkezes")
    public String showBejelentkezes(Model model) {
        return "bejelentkezes";
    }

    @PostMapping("/bejelentkezes-feldolgozas")
    public String bejelentkezes(Model model) {

        return "index";
    }


    @GetMapping("/kijelentkezes")
    public String kijelentkezes(Model model) {
        return "bejelentkezes";
    }
}
