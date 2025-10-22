package com.asztalfoglalas.asztalfoglalas.dto;

import jakarta.validation.constraints.NotNull;



import java.time.LocalDateTime;

public class FoglalasDTO {

    @NotNull
    private int felhasznaloId;

    @NotNull
    private int asztalId;

    @NotNull
    private int vendegek;

    @NotNull
    private LocalDateTime idopont;

    @NotNull
    private int meddig;

    public FoglalasDTO() {

    }

    public FoglalasDTO(int felhasznaloId, int asztalId, int vendegek, LocalDateTime idopont, int meddig) {
        this.felhasznaloId = felhasznaloId;
        this.asztalId = asztalId;
        this.vendegek = vendegek;
        this.idopont = idopont;
        this.meddig = meddig;
    }

    public int getFelhasznaloId() {
        return felhasznaloId;
    }

    public void setFelhasznaloId(int felhasznaloId) {
        this.felhasznaloId =felhasznaloId;
    }

    public int getAsztalId() {
        return asztalId;
    }

    public void setAsztalId(int asztalId) {
        this.asztalId = asztalId;
    }

    public int getVendegek() {
        return vendegek;
    }

    public void setVendegek(int vendegek) {
        this.vendegek = vendegek;
    }

    public LocalDateTime getIdopont() {
        return idopont;
    }

    public void setIdopont(LocalDateTime idopont) {
        this.idopont = idopont;
    }

    public int getMeddig() {
        return meddig;
    }

    public void setMeddig(int meddig) {
        this.meddig = meddig;
    }





}
