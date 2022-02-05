/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.controladores;

import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import com.ejercicio1.libreria_web.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author USER
 */
@Controller
public class ControladorIndex {

    @Autowired
    ServicioUsuario servicioUsuario;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String clave, @RequestParam Integer dni, @RequestParam String mail) {
        try {
            servicioUsuario.registrarUsuario(nombre, clave, dni, mail, true);
        } catch (MiExcepcion e) {
            System.out.println(e);
            System.out.println("Error al registrar el usuario");
        }
        return "inicio.html";
    }

}
