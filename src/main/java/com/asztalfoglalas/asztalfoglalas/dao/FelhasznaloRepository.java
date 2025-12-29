package com.asztalfoglalas.asztalfoglalas.dao;

import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FelhasznaloRepository extends JpaRepository<Felhasznalo, Integer> {

    //mivel nincs felhasználónév az adatbázisomban csak vezetéknév, keresztnév, ezért email alapján fogom
    // megkeresni és betölteni a felhasználót, ha létezik
    @Query("SELECT f FROM Felhasznalo f WHERE f.email = ?1")
    Optional<Felhasznalo> loadUserByUsername(String email);

    @Query("SELECT f.id FROM Felhasznalo f WHERE f.email = ?1")
    int findFelhasznaloIdByEmail(String email);

    @Query("SELECT f FROM Felhasznalo f WHERE f.email = ?1")
    Optional<Felhasznalo> findFelhasznaloByEmail(String email);
}
