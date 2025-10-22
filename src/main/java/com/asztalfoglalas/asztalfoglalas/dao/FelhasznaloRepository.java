package com.asztalfoglalas.asztalfoglalas.dao;

import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FelhasznaloRepository extends JpaRepository<Felhasznalo, Integer> {

    @Query("SELECT f FROM Felhasznalo f JOIN FETCH f.foglalasok WHERE f.id = ?1")
    public List<Felhasznalo> getFelhasznaloAndFoglalasokByFelhasznaloId(int id);


    //mivel nincs felhasználónév az adatbázisomban csak vezetéknév, keresztnév, ezért email alapján fogom
    // megkeresni és betölteni a felhasználót, ha létezik
    @Query("SELECT f FROM Felhasznalo f WHERE f.email = ?1")
    public Optional<Felhasznalo> loadUserByUsername(String email);

    @Query("SELECT f.id FROM Felhasznalo f WHERE f.email = ?1")
    public int findFelhasznaloIdByEmail(String email);
}
