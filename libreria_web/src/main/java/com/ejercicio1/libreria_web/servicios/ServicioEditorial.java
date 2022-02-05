/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.servicios;

import com.ejercicio1.libreria_web.entidades.Editorial;
import com.ejercicio1.libreria_web.repositorios.EditorialRepositorio;
import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class ServicioEditorial {

    @Autowired
    private EditorialRepositorio er;

    @Transactional
    public void guardarEditorial(String nombre, boolean alta) throws MiExcepcion {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(alta);

        er.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String id, String nombre, boolean alta) throws MiExcepcion {
        validar(nombre);
        Optional<Editorial> respuesta = er.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorial.setAlta(alta);

            er.save(editorial);
        } else {
            throw new MiExcepcion("No se encuentra la editorial con este id");
        }
    }

    @Transactional
    public void eliminarEditorial(String id) throws MiExcepcion {
        Optional<Editorial> respuesta = er.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            er.delete(editorial);
        } else {
            throw new MiExcepcion("No se encuentra la editorial con este id");
        }
    }

    @Transactional
    public Editorial buscarEditorialPorId(String id) {
        return er.buscarEditorialPorId(id);
    }

    @Transactional
    public Editorial buscarEditorialPorNombre(String nombre) {
        return er.buscarEditorialPorNombre(nombre);
    }

    @Transactional
    public List<Editorial> listarEditoriales() {
        return er.findAll();
    }

    public void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty()) {
            throw new MiExcepcion("Debe ingresar un nombre para el editorial");
        }
    }
}
