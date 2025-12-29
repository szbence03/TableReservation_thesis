package com.asztalfoglalas.asztalfoglalas.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asztal")
public class Asztal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ferohely")
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

    public void addFoglalas(Foglalas foglalas) {
        if(foglalasok == null) {
            foglalasok = new ArrayList<>();
        }
        foglalasok.add(foglalas);
    }

    @Override
    public String toString() {
        return "Asztal{" +
                "id=" + id +
                ", ferohely=" + ferohely +
                '}';
    }
}
