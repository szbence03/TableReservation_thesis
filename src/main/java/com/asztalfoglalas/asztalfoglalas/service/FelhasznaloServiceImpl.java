package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dao.FelhasznaloRepository;
import com.asztalfoglalas.asztalfoglalas.dto.FelhasznaloDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FelhasznaloServiceImpl implements FelhasznaloService{

    private final FelhasznaloRepository felhasznaloRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public FelhasznaloServiceImpl(FelhasznaloRepository felhasznaloRepository, BCryptPasswordEncoder passwordEncoder) {
        this.felhasznaloRepository = felhasznaloRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void save(FelhasznaloDTO felhasznalo) {
        Felhasznalo felhasznaloEntity = new Felhasznalo(
                felhasznalo.getKeresztnev(),
                felhasznalo.getVezeteknev(),
                felhasznalo.getEmail(),
                passwordEncoder.encode(felhasznalo.getJelszo()));
        felhasznaloRepository.save(felhasznaloEntity);
    }

    @Override
    public void deleteById(int id) {
        felhasznaloRepository.deleteById(id);
    }

    @Override
    public Felhasznalo findById(int id) {
        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.findById(id);

        Felhasznalo felhasznalo;

        if(optionalFelhasznalo.isPresent()) {
            felhasznalo = optionalFelhasznalo.get();
        } else {
            throw new RuntimeException("A keresett felhasználó nem létezik.");
        }
        return felhasznalo;
    }


    @Override
    public List<Felhasznalo> findAll() {
        return felhasznaloRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.loadUserByUsername(email);

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
        return felhasznaloRepository.findFelhasznaloIdByEmail(email);
    }

    @Override
    public Felhasznalo findFelhasznaloByEmail(String email) {
        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.findFelhasznaloByEmail(email);

        if(optionalFelhasznalo.isEmpty()) {
            throw new UsernameNotFoundException("A keresett felhasználó nem létezik!");
        }

        return optionalFelhasznalo.get();
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<Felhasznalo> optionalFelhasznalo = felhasznaloRepository.findFelhasznaloByEmail(email);

        return optionalFelhasznalo.isPresent();
    }


}
