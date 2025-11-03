package com.particulares.tp.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.particulares.tp.java.service.PersonaService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebMvc
public class SeguridadWeb {

    @Autowired
    private PersonaService personaService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login","/registrar", "/registro", "/css/**", "/js/**", "/").permitAll()
                //esto es a lo que le doy permiso de acceder sin estar logueado
                .anyRequest().authenticated() 
                // esto es lo que ncesito permiso para acceder 
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/inicio", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") //redirige al inicio 
                .permitAll() 
            );

        return http.build();
    }

    // @Bean
    // public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    //     return http.getSharedObject(AuthenticationManagerBuilder.class)
    //         .userDetailsService(personaDetailsService)
    //         .passwordEncoder(passwordEncoder())
    //         .and()
    //         .build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
