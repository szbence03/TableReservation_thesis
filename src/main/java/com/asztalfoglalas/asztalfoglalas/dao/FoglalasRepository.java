package com.asztalfoglalas.asztalfoglalas.dao;

import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FoglalasRepository extends JpaRepository<Foglalas, Integer> {

   /*
    @Query("SELECT f FROM Foglalas f WHERE mettol <= ?1 AND meddig >= ?2")
    public List<Foglalas> listFoglalasByDate(LocalDateTime mettol, LocalDateTime meddig);

    */

    @Query("SELECT f FROM Foglalas f WHERE f.felhasznalo.id = ?1 AND f.mettol <= ?3 AND f.meddig >= ?2")
    List<Foglalas> checkAktivFoglalasokByFelhasznaloId(int id, LocalDateTime mettol, LocalDateTime meddig);

    @Query("SELECT f FROM Foglalas f WHERE f.felhasznalo.id = ?1 AND f.meddig >= ?2")
    List<Foglalas> getAktivFoglalasokByFelhasznaloId(int id, LocalDateTime most);

    @Query("SELECT f FROM Foglalas f WHERE f.id = (SELECT MAX(f.id) FROM Foglalas f WHERE f.felhasznalo.id = ?1)")
    Optional<Foglalas> findLatestFoglalasByFelhasznaloId(int id);

}
