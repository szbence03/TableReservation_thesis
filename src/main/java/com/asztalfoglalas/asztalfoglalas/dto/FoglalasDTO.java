package com.asztalfoglalas.asztalfoglalas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

public class FoglalasDTO {

    @NotNull
    private Integer felhasznaloId;

    //Az asztal ellenőrzése külön történik az AsztalfoglalasControllerben,
    // mert 1 lépéssel később kérem be a felhasználótól
    private Integer asztalId;

    @NotNull
    @Min(value = 1, message = "A vendégek száma minimum 1 legyen!")
    @Max(value = 8, message = "A vendégek száma legfeljebb 8 lehet!")
    private Integer vendegek;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime idopont;

    @NotNull
    @Min(value = 30, message = "A minimális megengedett érték 30!")
    @Max(value = 180, message = "A maximális megengedett érték 180!")
    private Integer meddig;

    public FoglalasDTO() {

    }

    public FoglalasDTO(Integer felhasznaloId, Integer asztalId, Integer vendegek, Integer meddig, LocalDateTime idopont) {
        this.felhasznaloId = felhasznaloId;
        this.asztalId = asztalId;
        this.vendegek = vendegek;
        this.meddig = meddig;
        this.idopont = idopont;
    }

    public Integer getFelhasznaloId() {
        return felhasznaloId;
    }

    public void setFelhasznaloId(Integer felhasznaloId) {
        this.felhasznaloId = felhasznaloId;
    }

    public Integer getAsztalId() {
        return asztalId;
    }

    public void setAsztalId(Integer asztalId) {
        this.asztalId = asztalId;
    }

    public Integer getVendegek() {
        return vendegek;
    }

    public void setVendegek(Integer vendegek) {
        this.vendegek = vendegek;
    }

    public Integer getMeddig() {
        return meddig;
    }

    public void setMeddig(Integer meddig) {
        this.meddig = meddig;
    }

    public LocalDateTime getIdopont() {
        return idopont;
    }

    public void setIdopont(LocalDateTime idopont) {
        this.idopont = idopont;
    }


    public LocalDateTime getFoglalasVege() {
        return idopont.plusMinutes(meddig);
    }

}
