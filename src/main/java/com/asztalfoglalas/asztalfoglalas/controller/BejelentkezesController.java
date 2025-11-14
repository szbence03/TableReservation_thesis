package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
