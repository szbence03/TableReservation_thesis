package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dao.AsztalRepository;
import com.asztalfoglalas.asztalfoglalas.dao.FoglalasRepository;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsztalServiceImpl implements AsztalService {

    private final AsztalRepository asztalRepository;
    private final FoglalasRepository foglalasRepository;

    public AsztalServiceImpl(AsztalRepository asztalRepository, FoglalasRepository foglalasRepository) {
        this.asztalRepository = asztalRepository;
        this.foglalasRepository = foglalasRepository;
    }

    @Override
    public void save(Asztal asztal) {
        asztalRepository.save(asztal);
    }

    @Override
    public void deleteById(int id) {
        asztalRepository.deleteById(id);
    }

    @Override
    public Asztal findById(int id) {
        Optional<Asztal> optionalAsztal = asztalRepository.findById(id);

        Asztal asztal;

        if(optionalAsztal.isPresent()) {
            asztal = optionalAsztal.get();
        } else {
            throw new RuntimeException("Nem létezik a keresett asztal.");
        }
        return asztal;
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
