/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.controladores;

import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import com.ejercicio1.libreria_web.servicios.ServicioEditorial;
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
public class ControladorEditorial {

    @Autowired
    ServicioEditorial servicioEditorial;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/editoriales")
    public String editorial() {
        return "editoriales.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @PostMapping("/editoriales")
    public String guardarEditorial(ModelMap modelo, @RequestParam String nombre) throws MiExcepcion {
        try {
            servicioEditorial.guardarEditorial(nombre, true);
            modelo.put("exito", "EXITO!!Editorial registrada exitosamente!!");
            return "editoriales.html";
        } catch (MiExcepcion e) {
            modelo.put("error", "ERROR!!La editorial no ha podido ser registrada, vuelva a intentarlo");
            return "editoriales.html";
        }
    }

}
