package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dto.FelhasznaloDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface FelhasznaloService extends UserDetailsService {

    public void save(FelhasznaloDTO felhasznalo);

    public void deleteById(int id);

    public Felhasznalo findById(int id);

    public List<Felhasznalo> findAll();

    public List<Felhasznalo> getFelhasznaloAndFoglalasokById(int id);

    public UserDetails loadUserByUsername(String email);

    public int findFelhasznaloIdByEmail(String email);
}
