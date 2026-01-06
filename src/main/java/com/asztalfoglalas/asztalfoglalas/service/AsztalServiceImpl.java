package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.repository.AsztalRepository;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsztalServiceImpl implements AsztalService {

    private final AsztalRepository asztalRepository;

    @Autowired
    public AsztalServiceImpl(AsztalRepository asztalRepository) {
        this.asztalRepository = asztalRepository;
    }


    @Override
    public Asztal findById(int id) {

        return asztalRepository.findById(id).orElse(null);

    }

    @Override
    public List<Asztal> findAll() {
        return asztalRepository.findAll();
    }


    @Override
    public List<Asztal> getSzabadAsztalok(LocalDateTime mettol, LocalDateTime meddig) {
        return asztalRepository.getSzabadAsztalok(mettol, meddig);
    }

    @Override
    public List<Asztal> getFoglaltAsztalok(LocalDateTime mettol, LocalDateTime meddig) {
        return asztalRepository.getFoglaltAsztalok(mettol, meddig);
    }
}
