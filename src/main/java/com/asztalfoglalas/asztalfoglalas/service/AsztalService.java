package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;

import java.time.LocalDateTime;
import java.util.List;

public interface AsztalService {

    Asztal findById(int id);

    List<Asztal> findAll();

    List<Asztal> getSzabadAsztalok(LocalDateTime mettol, LocalDateTime meddig);

    List<Asztal> getFoglaltAsztalok(LocalDateTime mettol, LocalDateTime meddig);

    boolean checkAsztalFoglaltE(Asztal asztal, LocalDateTime mettol, LocalDateTime meddig);

    boolean checkAsztalOptimalisE(FoglalasDTO foglalas, Asztal asztal);
}
