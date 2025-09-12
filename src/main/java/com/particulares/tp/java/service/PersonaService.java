package com.particulares.tp.java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;

import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.repository.PersonaRepository;

//import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class PersonaService implements UserDetailsService {
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        Persona persona = personaRepository.buscarPorEmail(email);
       
        if (persona == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        if (persona.getRol() == null) {
            throw new UsernameNotFoundException("El usuario no tiene rol asignado");
        }


        List<GrantedAuthority> permisos = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ persona.getRol().toString());
        
        permisos.add(p);

        // ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // HttpSession session = attr.getRequest().getSession(true);
        // session.setAttribute("personaSession", persona);
        
        return new User(persona.getEmail(), persona.getClave() ,permisos);

    }

    
}