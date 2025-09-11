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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.repository.PersonaRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class PersonaService implements UserDetailsService {
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
        Persona persona = personaRepository.buscarPorEmail(email);
       
        if (persona == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<GrantedAuthority> permisos = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ persona.getRol().toString());
        
        permisos.add(p);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("personaSession", persona);
        
        return new User(persona.getEmail(), persona.getClave() ,permisos);
        
        // Otra forma de hacerlo:
        // Persona persona1 = personaRepository.buscarPorEmail(email);

        // if (persona1 == null) {
        //     throw new UsernameNotFoundException("No se encontró ninguna persona con el email: " + email);
        // }

        // return User.builder()
        //     .username(persona1.getEmail())
        //     .password(persona1.getClave()) // asegurate de que esté encriptado
        //     .roles(persona1.getRol().toString()) // "ALUMNO" o "PROFESOR"
        //     .build();

    }

    
}