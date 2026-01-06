package com.asztalfoglalas.asztalfoglalas.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Asztal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ferohely;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "asztal")
    private List<Foglalas> foglalasok;

    public Asztal() {

    }

    public Asztal(int ferohely) {
        this.ferohely = ferohely;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFerohely() {
        return ferohely;
    }

    public void setFerohely(int ferohely) {
        this.ferohely = ferohely;
    }

    public List<Foglalas> getFoglalasok() {
        return foglalasok;
    }

    public void setFoglalasok(List<Foglalas> foglalasok) {
        this.foglalasok = foglalasok;
    }

    @Override
    public String toString() {
        return "Asztal{" +
                "id=" + id +
                ", ferohely=" + ferohely +
                '}';
    }
}
