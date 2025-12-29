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
                              RedirectAttributes redirectAttributes, Model model) {

        if(authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()){
            return "regisztracio";
        }

        if(felhasznaloService.existsByEmail(felhasznaloDTO.getEmail())) {
            redirectAttributes.addFlashAttribute("hiba", "Ezzel az email-címmel már regisztráltak!");
            return "redirect:/regisztracio";
        }

        if(!felhasznaloDTO.getJelszo().equals(felhasznaloDTO.getJelszoMegerosites())) {
            redirectAttributes.addFlashAttribute("hiba", "A jelszavaid nem egyeznek!");
            return "redirect:/regisztracio";
            }
            
        felhasznaloService.save(felhasznaloDTO);
        return "redirect:/bejelentkezes";
        }
}
