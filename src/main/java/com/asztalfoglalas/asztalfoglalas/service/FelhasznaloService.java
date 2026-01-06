package com.asztalfoglalas.asztalfoglalas.service;

import com.asztalfoglalas.asztalfoglalas.dto.FelhasznaloDTO;
import com.asztalfoglalas.asztalfoglalas.entity.Felhasznalo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface FelhasznaloService extends UserDetailsService {

    void save(FelhasznaloDTO felhasznalo);

    UserDetails loadUserByUsername(String email);

    int findFelhasznaloIdByEmail(String email);

    Felhasznalo findFelhasznaloByEmail(String email);

    boolean existsByEmail(String email);
}
