/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.controladores;

import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import com.ejercicio1.libreria_web.servicios.ServicioAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author USER
 */
@Controller
public class ControladorAutor {

    @Autowired
    private ServicioAutor servicioAutor;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/autores")
    public String autor() {
        return "autores.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @PostMapping("/autores")
    public String guardarAutor(ModelMap modelo, @RequestParam String nombre) throws MiExcepcion {
        try {
            servicioAutor.registrarAutor(nombre, true);
            modelo.put("exito", "EXITO!!Autor registrado exitosamente!!");
            return "autores.html";
        } catch (MiExcepcion e) {
            modelo.put("error", "ERROR!!El Autori no ha podido ser registrado, vuelva a intentarlo");
            return "autores.html";
        }
    }

}
