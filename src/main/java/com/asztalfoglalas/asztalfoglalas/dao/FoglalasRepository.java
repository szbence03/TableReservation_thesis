package com.asztalfoglalas.asztalfoglalas.dao;

import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface FoglalasRepository extends JpaRepository<Foglalas, Integer> {

    @Query("SELECT f FROM Foglalas f WHERE mettol <= ?1 AND meddig >= ?2")
    public List<Foglalas> listFoglalasByDate(LocalDateTime mettol, LocalDateTime meddig);
}
