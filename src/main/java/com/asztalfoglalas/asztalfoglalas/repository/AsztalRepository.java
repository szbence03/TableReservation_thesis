package com.asztalfoglalas.asztalfoglalas.repository;

import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AsztalRepository extends JpaRepository<Asztal, Integer> {

    @Query("SELECT a FROM Asztal a WHERE a.id NOT IN (SELECT f.asztal.id FROM Foglalas f WHERE f.mettol <= ?2 AND f.meddig >= ?1)")
    List<Asztal> getSzabadAsztalok(LocalDateTime mettol, LocalDateTime meddig);

    @Query("SELECT a FROM Asztal a WHERE a.id IN (SELECT f.asztal.id FROM Foglalas f WHERE f.mettol <= ?2 AND f.meddig >= ?1)")
    List<Asztal> getFoglaltAsztalok(LocalDateTime mettol, LocalDateTime meddig);


}
