package com.asztalfoglalas.asztalfoglalas.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Foglalas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "felhasznalo_id")
    private Felhasznalo felhasznalo;

    @ManyToOne
    @JoinColumn(name = "asztal_id")
    private Asztal asztal;

    private int vendegek;

    private LocalDateTime mettol;

    private LocalDateTime meddig;


    public Foglalas() {
    }

    public Foglalas(Felhasznalo felhasznalo, Asztal asztal, int vendegek, LocalDateTime mettol, LocalDateTime meddig) {
        this.felhasznalo = felhasznalo;
        this.asztal = asztal;
        this.vendegek = vendegek;
        this.mettol = mettol;
        this.meddig = meddig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Felhasznalo getFelhasznalo() {
        return felhasznalo;
    }

    public void setFelhasznalo(Felhasznalo felhasznalo) {
        this.felhasznalo = felhasznalo;
    }

    public Asztal getAsztal() {
        return asztal;
    }

    public void setAsztal(Asztal asztal) {
        this.asztal = asztal;
    }

    public LocalDateTime getMettol() {
        return mettol;
    }

    public void setMettol(LocalDateTime mettol) {
        this.mettol = mettol;
    }

    public LocalDateTime getMeddig() {
        return meddig;
    }

    public void setMeddig(LocalDateTime meddig) {
        this.meddig = meddig;
    }

    public int getVendegek() {
        return vendegek;
    }

    public void setVendegek(int vendegek) {
        this.vendegek = vendegek;
    }

    @Override
    public String toString() {
        return "Foglalas{" +
                "felhasznalo=" + felhasznalo.getId() +
                ", asztal=" + asztal.getId() +
                ", vendegek=" + vendegek +
                ", mettol=" + mettol +
                ", meddig=" + meddig +
                '}';
    }
}
