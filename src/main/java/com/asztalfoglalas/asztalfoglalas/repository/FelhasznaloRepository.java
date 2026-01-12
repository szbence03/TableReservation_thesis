package com.asztalfoglalas.asztalfoglalas.repository;

import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FelhasznaloRepository extends JpaRepository<Felhasznalo, Integer> {


    @Query("SELECT f.id FROM Felhasznalo f WHERE f.email = ?1")
    Optional<Integer> findFelhasznaloIdByEmail(String email);

    //mivel nincs felhasználónév az adatbázisomban csak vezetéknév, keresztnév, ezért email alapján fogom
    // megkeresni és betölteni a felhasználót a sessionbe, ha létezik
    @Query("SELECT f FROM Felhasznalo f WHERE f.email = ?1")
    Optional<Felhasznalo> findFelhasznaloByEmail(String email);
}
