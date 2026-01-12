package com.asztalfoglalas.asztalfoglalas.service.impl;

import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.repository.AsztalRepository;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.service.AsztalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public boolean checkAsztalFoglaltE(Asztal asztal, LocalDateTime mettol, LocalDateTime meddig) {
        List<Asztal> foglaltAsztalok = asztalRepository.getFoglaltAsztalok(mettol, meddig);
        return foglaltAsztalok.contains(asztal);
    }

    @Override
    public boolean checkAsztalOptimalisE(FoglalasDTO foglalas, Asztal asztal) {
        List<Asztal> szabadAsztalok = asztalRepository.getSzabadAsztalok(foglalas.getIdopont(), foglalas.getFoglalasVege());
        boolean optimalisE = (asztal.getFerohely() - foglalas.getVendegek()) > 2 &&
                (szabadAsztalok.stream().filter(a -> (a.getFerohely() - foglalas.getVendegek()) < 2).count() > 0);
        return optimalisE;
    }
}
