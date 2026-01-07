package com.asztalfoglalas.asztalfoglalas.repository;

import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FoglalasRepository extends JpaRepository<Foglalas, Integer> {

    @Query("SELECT f FROM Foglalas f WHERE f.felhasznalo.id = ?1 AND f.mettol <= ?3 AND f.meddig >= ?2")
    List<Foglalas> checkAktivFoglalasokByFelhasznaloId(int id, LocalDateTime mettol, LocalDateTime meddig);

    @Query("SELECT f FROM Foglalas f WHERE f.felhasznalo.id = ?1 AND f.meddig >= ?2 ORDER BY f.mettol ASC")
    List<Foglalas> getAktivFoglalasokByFelhasznaloId(int id, LocalDateTime most);

    @Modifying
    @Query("DELETE FROM Foglalas f WHERE f.meddig < ?1")
    int deleteLejartFoglalasok(LocalDateTime most);

}
