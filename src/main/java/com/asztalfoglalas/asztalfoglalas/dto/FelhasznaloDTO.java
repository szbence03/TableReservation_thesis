package com.asztalfoglalas.asztalfoglalas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FelhasznaloDTO {

    @NotBlank
    @Size(min = 2, message = "Minimum 2 karakterből álljon!")
    private String keresztnev;

    @NotBlank
    @Size(min = 2, message = "Minimum 2 karakterből álljon!")
    private String vezeteknev;

    @NotBlank
    @Size(min = 6, message = "Minimum 6 karakterből álljon!")
    private String email;

    @NotBlank
    @Size(min = 8, message = "Legyen minimum 8 karakter hosszú!")
    private String jelszo;

    @NotBlank
    @Size(min = 8, message = "Legyen minimum 8 karakter hosszú!")
    private String jelszoMegerosites;

    public FelhasznaloDTO() {

    }

    public FelhasznaloDTO(String keresztnev, String vezeteknev, String email, String jelszo, String jelszoMegerosites) {
        this.keresztnev = keresztnev;
        this.vezeteknev = vezeteknev;
        this.email = email;
        this.jelszo = jelszo;
        this.jelszoMegerosites = jelszoMegerosites;
    }

    public String getKeresztnev() {
        return keresztnev;
    }

    public void setKeresztnev(String keresztnev) {
        this.keresztnev = keresztnev;
    }

    public String getVezeteknev() {
        return vezeteknev;
    }

    public void setVezeteknev(String vezeteknev) {
        this.vezeteknev = vezeteknev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getJelszoMegerosites() {
        return jelszoMegerosites;
    }

    public void setJelszoMegerosites(String jelszoMegerosites) {
        this.jelszoMegerosites = jelszoMegerosites;
    }
}
