package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BejelentkezesController {

    FelhasznaloService felhasznaloService;

    @Autowired
    public BejelentkezesController(FelhasznaloService felhasznaloService) {
        this.felhasznaloService = felhasznaloService;
    }

    @GetMapping("/bejelentkezes")
    public String showBejelentkezes(Authentication authentication) {

        if(authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "bejelentkezes";
    }
}
