package com.asztalfoglalas.asztalfoglalas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FelhasznaloDTO {

    @NotNull
    @Size(min = 1, message = "kötelező mező")
    private String keresztnev;

    @NotNull
    @Size(min = 1, message = "kötelező mező")
    private String vezeteknev;

    @NotNull
    @Size(min = 6, message = "kötelező mező")
    private String email;

    @NotNull
    @Size(min = 8, message = "legyen minimum 8 karakter hosszú")
    private String jelszo;

    @NotNull
    @Size(min = 8, message = "legyen minimum 8 karakter hosszú")
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
