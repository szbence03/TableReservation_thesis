package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;

import java.time.LocalDateTime;
import java.util.List;

public interface FoglalasService {

    Foglalas save(FoglalasDTO foglalas);

    void deleteById(int id);

    Foglalas findById(int id);

    boolean checkAktivFoglalasByFelhasznaloId(int id, LocalDateTime mettol, LocalDateTime meddig);

    List<Foglalas> getAktivFoglalasokByFelhasznaloId(int id);

    boolean isVendegSzamTobbMintFerohely(FoglalasDTO foglalas, Asztal asztal);
}
