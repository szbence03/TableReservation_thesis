package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

public interface FoglalasService {

    void save(FoglalasDTO foglalas, Principal principal);

    void deleteById(int id);

    Foglalas findById(int id);

    List<Foglalas> findAll();

    public List<Foglalas> listFoglalasByDate(LocalDateTime mettol, LocalDateTime meddig);
}
