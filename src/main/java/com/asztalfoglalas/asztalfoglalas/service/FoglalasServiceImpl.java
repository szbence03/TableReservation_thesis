package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dao.AsztalRepository;
import com.asztalfoglalas.asztalfoglalas.dao.FelhasznaloRepository;
import com.asztalfoglalas.asztalfoglalas.dao.FoglalasRepository;
import com.asztalfoglalas.asztalfoglalas.dto.FoglalasDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Asztal;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import com.asztalfoglalas.asztalfoglalas.entity.Foglalas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FoglalasServiceImpl implements FoglalasService{

    private final FoglalasRepository foglalasRepository;
    private final FelhasznaloRepository felhasznaloRepository;
    private final AsztalRepository asztalRepository;

    @Autowired
    public FoglalasServiceImpl(FoglalasRepository foglalasRepository, FelhasznaloRepository felhasznaloRepository, AsztalRepository asztalRepository) {
        this.foglalasRepository = foglalasRepository;
        this.felhasznaloRepository = felhasznaloRepository;
        this.asztalRepository = asztalRepository;
    }

    @Override
    public void save(FoglalasDTO foglalas) {

        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.findById(foglalas.getFelhasznaloId());
        Optional<Asztal> optionalAsztal = asztalRepository.findById(foglalas.getAsztalId());

        if(optionalFelhasznalo.isPresent() && optionalAsztal.isPresent()) {
            Felhasznalo felhasznalo = optionalFelhasznalo.get();
            Asztal asztal = optionalAsztal.get();

            Foglalas foglalasEntity = new Foglalas(
                    felhasznalo,
                    asztal,
                    foglalas.getVendegek(),
                    foglalas.getIdopont(),
                    foglalas.getMeddig());

            foglalasRepository.save(foglalasEntity);
        } else {
            throw new RuntimeException("Hiba: a felhasználó vagy az asztal nem létezik a beolvasott azonosítóval.");
        }

    }

    @Override
    public void deleteById(int id) {
        foglalasRepository.deleteById(id);
    }

    @Override
    public Foglalas findById(int id) {
        Optional<Foglalas> optionalFoglalas = foglalasRepository.findById(id);

        Foglalas foglalas;

        if(optionalFoglalas.isPresent()) {
            foglalas = optionalFoglalas.get();
        } else {
            throw new RuntimeException("A keresett foglalás nem létezik.");
        }
        return foglalas;
    }

    @Override
    public List<Foglalas> findAll() {
        return foglalasRepository.findAll();
    }

    @Override
    public List<Foglalas> listFoglalasByDate(LocalDateTime mettol, LocalDateTime meddig) {
        return foglalasRepository.listFoglalasByDate(mettol, meddig);
    }

    @Override
    public boolean checkAktivFoglalasByFelhasznaloId(int id, LocalDateTime mettol, LocalDateTime meddig) {
        List<Foglalas> f =  foglalasRepository.checkAktivFoglalasByFelhasznaloId(id,mettol,meddig);
        if(!f.isEmpty()) {
            for(Foglalas ff : f) {
                System.out.println(ff.getMeddig());
            }
        }
        return !foglalasRepository.checkAktivFoglalasByFelhasznaloId(id, mettol, meddig).isEmpty();
    }


}
