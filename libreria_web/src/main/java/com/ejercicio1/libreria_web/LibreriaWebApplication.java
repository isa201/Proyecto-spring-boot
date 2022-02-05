package com.ejercicio1.libreria_web;

import com.ejercicio1.libreria_web.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibreriaWebApplication {

    @Autowired
    private ServicioUsuario servicioUsuario;

    public static void main(String[] args) {
        SpringApplication.run(LibreriaWebApplication.class, args);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(servicioUsuario)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}
