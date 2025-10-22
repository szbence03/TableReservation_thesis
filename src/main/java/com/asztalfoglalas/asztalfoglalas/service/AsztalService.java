package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.entity.Asztal;

import java.time.LocalDateTime;
import java.util.List;

public interface AsztalService {

    void save(Asztal asztal);

    void deleteById(int id);

    Asztal findById(int id);

    List<Asztal> findAll();

    public List<Asztal> getSzabadAsztalok(LocalDateTime mettol, LocalDateTime meddig);

    public List<Asztal> getFoglaltAsztalok(LocalDateTime mettol, LocalDateTime meddig);
}
