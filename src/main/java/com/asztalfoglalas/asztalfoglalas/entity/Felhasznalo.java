package com.asztalfoglalas.asztalfoglalas.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "felhasznalo")
public class Felhasznalo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "keresztnev")
    private String keresztnev;

    @Column(name = "vezeteknev")
    private String vezeteknev;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "jelszo")
    private String jelszo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "felhasznalo")
    private List<Foglalas> foglalasok;

    public Felhasznalo() {

    }

    public Felhasznalo(String keresztnev, String vezeteknev, String email, String jelszo) {
        this.keresztnev = keresztnev;
        this.vezeteknev = vezeteknev;
        this.email = email;
        this.jelszo = jelszo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Felhasznalo{" +
                "id=" + id +
                ", keresztnev='" + keresztnev + '\'' +
                ", vezeteknev='" + vezeteknev + '\'' +
                ", email='" + email + '\'' +
                ", jelszo='" + jelszo + '\'' +
                '}';
    }
}
