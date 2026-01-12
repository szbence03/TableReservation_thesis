package com.asztalfoglalas.asztalfoglalas.service.impl;

import com.asztalfoglalas.asztalfoglalas.repository.FelhasznaloRepository;
import com.asztalfoglalas.asztalfoglalas.dto.FelhasznaloDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import com.asztalfoglalas.asztalfoglalas.service.FelhasznaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FelhasznaloServiceImpl implements FelhasznaloService {

    private final FelhasznaloRepository felhasznaloRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public FelhasznaloServiceImpl(FelhasznaloRepository felhasznaloRepository, BCryptPasswordEncoder passwordEncoder) {
        this.felhasznaloRepository = felhasznaloRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void save(FelhasznaloDTO felhasznalo) {

        if(existsByEmail(felhasznalo.getEmail())) {
            throw new RuntimeException("Ezzel az email-címmel már regisztráltak!");
        }
        if(!felhasznalo.getJelszo().equals(felhasznalo.getJelszoMegerosites())) {
            throw new RuntimeException("A jelszavaid nem egyeznek!");
        }

        Felhasznalo felhasznaloEntity = new Felhasznalo(
                felhasznalo.getKeresztnev(),
                felhasznalo.getVezeteknev(),
                felhasznalo.getEmail(),
                passwordEncoder.encode(felhasznalo.getJelszo()));
        felhasznaloRepository.save(felhasznaloEntity);
    }

    @Override
    public Felhasznalo findById(int id) {
        return felhasznaloRepository.findById(id).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.findFelhasznaloByEmail(email);

        if(optionalFelhasznalo.isEmpty()) {
            throw new UsernameNotFoundException("A keresett felhasználó nem létezik!");
        }

        Felhasznalo felhasznalo = optionalFelhasznalo.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(felhasznalo.getEmail())
                .password(felhasznalo.getJelszo())
                .build();
    }

    @Override
    public int findFelhasznaloIdByEmail(String email) {
        return felhasznaloRepository.findFelhasznaloIdByEmail(email).orElse(-1);
    }

    @Override
    public Felhasznalo findFelhasznaloByEmail(String email) {

        return felhasznaloRepository.findFelhasznaloByEmail(email).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.findFelhasznaloByEmail(email);

        return optionalFelhasznalo.isPresent();
    }


}
