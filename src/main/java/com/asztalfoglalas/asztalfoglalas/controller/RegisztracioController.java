package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.dto.FelhasznaloDTO;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisztracioController {

    FelhasznaloService felhasznaloService;

    @Autowired
    public RegisztracioController(FelhasznaloService felhasznaloService) {
        this.felhasznaloService = felhasznaloService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/regisztracio")
    public String showRegisztracio(Model model, Authentication authentication) {

        if(authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("felhasznalo", new FelhasznaloDTO());
        return "regisztracio";
    }

    @PostMapping("/regisztracio-feldolgozas")
    public String regisztacio(@Valid @ModelAttribute("felhasznalo")FelhasznaloDTO felhasznaloDTO,
                              BindingResult bindingResult, Authentication authentication,
                              RedirectAttributes redirectAttributes) {

        if(authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()){
            return "regisztracio";
        }

        try {
            felhasznaloService.save(felhasznaloDTO);
            redirectAttributes.addFlashAttribute("siker", "Sikeres regisztráció!");
            return "redirect:/bejelentkezes";

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("hiba", e.getMessage());
            return "redirect:/regisztracio";
        }
    }
}
