package com.asztalfoglalas.asztalfoglalas.service.impl;

import com.asztalfoglalas.asztalfoglalas.repository.FoglalasRepository;
import com.asztalfoglalas.asztalfoglalas.service.FoglalasCleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class FoglalasCleanupServiceImpl implements FoglalasCleanupService {

    private final FoglalasRepository foglalasRepository;

    @Autowired
    public FoglalasCleanupServiceImpl(FoglalasRepository foglalasRepository) {
        this.foglalasRepository = foglalasRepository;
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void deleteLejartFoglalasok() {
        LocalDateTime most = LocalDateTime.now();
        int toroltFoglalasokSzama = foglalasRepository.deleteLejartFoglalasok(most);

        System.out.println("Törölt sorok száma: " + toroltFoglalasokSzama);
    }
}
