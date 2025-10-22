package com.asztalfoglalas.asztalfoglalas.controller;

import com.asztalfoglalas.asztalfoglalas.dto.FelhasznaloDTO;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String showRegisztracio(Model model) {
        model.addAttribute("felhasznalo", new FelhasznaloDTO());
        return "regisztracio";
    }

    @PostMapping("/regisztracio-feldolgozas")
    public String regisztacio(@Valid @ModelAttribute("felhasznalo")FelhasznaloDTO felhasznaloDTO, BindingResult bindingResult,
                              HttpSession session, Model model) {

        if (bindingResult.hasErrors()){
            return "regisztracio";
        }
        try {
            UserDetails felhasznalo = felhasznaloService.loadUserByUsername(felhasznaloDTO.getEmail());
                model.addAttribute("felhasznalo", felhasznaloDTO);
                return "redirect:/regisztracio?email_error";

        } catch(UsernameNotFoundException error) {
            if(!felhasznaloDTO.getJelszo().equals(felhasznaloDTO.getJelszoMegerosites())) {
                model.addAttribute("felhasznalo", felhasznaloDTO);
                return "redirect:/regisztracio?jelszo_error";
            }

            System.out.println("Felhasználó mentése");
            felhasznaloService.save(felhasznaloDTO);

            session.setAttribute("felhasznalo", felhasznaloDTO);
            return "redirect:/regisztracio?success";
        }

    }
}
