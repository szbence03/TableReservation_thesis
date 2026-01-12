package com.asztalfoglalas.asztalfoglalas.service.impl;

import com.asztalfoglalas.asztalfoglalas.repository.FoglalasRepository;
import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import com.asztalfoglalas.asztalfoglalas.service.AsztalService;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import com.asztalfoglalas.asztalfoglalas.service.FoglalasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoglalasServiceImpl implements FoglalasService {

    private final FoglalasRepository foglalasRepository;
    private final FelhasznaloService felhasznaloService;
    private final AsztalService asztalService;

    @Autowired
    public FoglalasServiceImpl(FoglalasRepository foglalasRepository, FelhasznaloService felhasznaloService, AsztalService asztalService) {
        this.foglalasRepository = foglalasRepository;
        this.felhasznaloService = felhasznaloService;
        this.asztalService = asztalService;
    }

    @Override
    @Transactional
    public Foglalas save(FoglalasDTO foglalas) {

        Felhasznalo felhasznalo = felhasznaloService.findById(foglalas.getFelhasznaloId());
        Asztal asztal = asztalService.findById(foglalas.getAsztalId());

        //létezik-e a kiválasztott asztal és felhasználó (html érték átírása ellen ellenőrzés)
        if(felhasznalo == null || asztal == null) {
            throw new RuntimeException("A felhasználó vagy az asztal nem létezik a beolvasott azonosítóval!");
        }

        //asztal kiszűrése id alapján, hogy az adott időközben ne legyen már lefoglalva
        if(asztalService.checkAsztalFoglaltE(asztal, foglalas.getIdopont(), foglalas.getFoglalasVege())) {
            throw new RuntimeException("Ez az asztal már foglalt a kiválasztott időben!");
        }
        //vendégek száma ne haladja túl a kiválasztott asztal férőhelyét
        if(isVendegSzamTobbMintFerohely(foglalas, asztal)) {
            throw new RuntimeException("A kiválasztott asztal túl kicsi a vendégeid számához képest!");
        }
        //ha a vendégek száma jóval kisebb (pl. <2) mint az asztal férőhelye, csak akkor fogadja el a foglalást,
        // ha nincs szabad asztal kevesebb férőhellyel (a vendégek számánál nem kevesebbel) az időpontban.
        if(asztalService.checkAsztalOptimalisE(foglalas, asztal)) {
            throw new RuntimeException("A kiválasztott asztal túl nagy férőhellyel rendelkezik a vendégeid számához képest!");
        }

        Foglalas foglalasEntity = new Foglalas(
                felhasznalo,
                asztal,
                foglalas.getVendegek(),
                foglalas.getIdopont(),
                foglalas.getFoglalasVege());

        return foglalasRepository.save(foglalasEntity);
    }

    @Override
    public void deleteById(int id) {
        foglalasRepository.deleteById(id);
    }

    @Override
    public Foglalas findById(int id) {
        return foglalasRepository.findById(id).orElse(null);
    }


    @Override
    public boolean checkAktivFoglalasByFelhasznaloId(int id, LocalDateTime mettol, LocalDateTime meddig) {

        return !foglalasRepository.checkAktivFoglalasokByFelhasznaloId(id, mettol, meddig).isEmpty();
    }

    @Override
    public List<Foglalas> getAktivFoglalasokByFelhasznaloId(int id) {
        return foglalasRepository.getAktivFoglalasokByFelhasznaloId(id, LocalDateTime.now());
    }

    @Override
    public boolean isVendegSzamTobbMintFerohely(FoglalasDTO foglalas, Asztal asztal) {
        return  foglalas.getVendegek() > asztal.getFerohely();
    }

}
