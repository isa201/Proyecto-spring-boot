/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.servicios;

import com.ejercicio1.libreria_web.entidades.Autor;
import com.ejercicio1.libreria_web.entidades.Editorial;
import com.ejercicio1.libreria_web.entidades.Imagen;
import com.ejercicio1.libreria_web.entidades.Libro;
import com.ejercicio1.libreria_web.repositorios.LibroRepositorio;
import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author USER
 */
@Service
public class ServicioLibro {

    @Autowired
    private LibroRepositorio lr;

    @Autowired
    ServicioImagen si;

    /*
    Transactional se debe colocar en los metodos que usen el repositorio para consultar en la base de datos
    o que usen la base de datos
     */
    @Transactional
    public void registrarLibro(MultipartFile archivo, long isbn, String nombre, Integer anio, Integer ejemplares, Integer ejemplaresRestantes, Integer ejemplaresPrestados, boolean alta, Autor autor, Editorial editorial) throws MiExcepcion, IOException {

        validar(isbn, nombre, anio, autor, editorial);

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setNombre(nombre);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setAlta(alta);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        Imagen imagen = si.guardar(archivo);
        libro.setImagen(imagen);

        lr.save(libro);
    }

    @Transactional
    public void modificarLibro(MultipartFile archivo, String id, String nombre, Integer anio, Integer ejemplares, Integer ejemplaresRestantes, Integer ejemplaresPrestados, boolean alta, Autor autor, Editorial editorial) throws MiExcepcion {
        /*
        puedo usar este metodo creado en el repositorio
        Libro libro = lr.buscarLibroPorIsbn(isbn);
        o este si quiero traerlo por id
        Libro libro = lr.findById(id).get();
         */
        validar2(nombre, anio, autor, editorial);

        Optional<Libro> respuesta = lr.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setNombre(nombre);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setAlta(alta);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            String idImagen = null;
            if (libro.getImagen() != null) {
                idImagen = libro.getImagen().getId();
            }
            Imagen imagen = si.actualizar(idImagen, archivo);

            libro.setImagen(imagen);

            lr.save(libro);
        } else {
            throw new MiExcepcion("No se encontro el libro con ese id");
        }
    }

    @Transactional
    public void eliminarLibro(String id) throws MiExcepcion {
        Optional<Libro> respuesta = lr.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            lr.delete(libro);
        } else {
            throw new MiExcepcion("No se encontro el libro con ese id");
        }
    }

    @Transactional
    public Libro buscarLibroPorId(String id) {
        return lr.buscarLibroPorId(id);
    }

    @Transactional
    public List<Libro> listarLibros() {
        return lr.findAll();
    }

    @Transactional
    public Libro darAlta(String id) {
        Libro libro = buscarLibroPorId(id);
        libro.setAlta(true);
        return lr.save(libro);
    }

    @Transactional
    public Libro darBaja(String id) {
        Libro libro = buscarLibroPorId(id);
        libro.setAlta(false);
        return lr.save(libro);
    }

    public void validar(long isbn, String nombre, Integer anio, Autor autor, Editorial editorial) throws MiExcepcion {
        if (isbn == 0) {
            throw new MiExcepcion("Error al registrar el libro.Falta el ISBN");
        }
        if (nombre.isEmpty()) {
            throw new MiExcepcion("Error al registar el libro.Falta el nombre del libro");
        }
        if (anio == 0) {
            throw new MiExcepcion("Error al registrar el libro.Debe ingresar un año valido");
        }
        if (autor == null || editorial == null) {
            throw new MiExcepcion("Error al registrar el libro.Verifique que se ingrese un autor y un editorial correctamente");
        }
    }

    public void validar2(String nombre, Integer anio, Autor autor, Editorial editorial) throws MiExcepcion {

        if (nombre.isEmpty()) {
            throw new MiExcepcion("Error al registar el libro.Falta el nombre del libro");
        }
        if (anio == 0) {
            throw new MiExcepcion("Error al registrar el libro.Debe ingresar un año valido");
        }
        if (autor == null || editorial == null) {
            throw new MiExcepcion("Error al registrar el libro.Verifique que se ingrese un autor y un editorial correctamente");
        }
    }
}
