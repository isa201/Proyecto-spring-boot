/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.servicios;

import com.ejercicio1.libreria_web.entidades.Autor;
import com.ejercicio1.libreria_web.repositorios.AutorRepositorio;
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
public class ServicioAutor {

    @Autowired
    private AutorRepositorio ar;

    @Transactional
    public void registrarAutor(String nombre, boolean alta) throws MiExcepcion {

        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(alta);
        ar.save(autor);
    }

    @Transactional
    public void modificarAutor(String id, String nombre, boolean alta) throws MiExcepcion {
        validar(nombre);
        Optional<Autor> respuesta = ar.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autor.setAlta(alta);
            ar.save(autor);
        } else {
            throw new MiExcepcion("No se encontro el autor con ese id");
        }

    }

    @Transactional
    public void eliminarAutor(String id) throws MiExcepcion {
        Optional<Autor> respuesta = ar.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            ar.delete(autor);
        } else {
            throw new MiExcepcion("No se encontro el autor con ese id");
        }
    }

    @Transactional
    public Autor buscarPorId(String id) {
        return ar.buscarAutorPorId(id);
    }

    @Transactional
    public Autor buscarPorNombre(String nombre) {
        return ar.buscarAutorPorNombre(nombre);
    }

    @Transactional
    public List<Autor> listarAutores() {
        return ar.findAll();
    }

    public void validar(String nombre) throws MiExcepcion {
        if (nombre.isEmpty()) {
            throw new MiExcepcion("Debe ingresar un nombre para el autor");
        }

    }
}
