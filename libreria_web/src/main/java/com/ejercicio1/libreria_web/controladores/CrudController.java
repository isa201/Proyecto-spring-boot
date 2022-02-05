/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.controladores;

import com.ejercicio1.libreria_web.entidades.Autor;
import com.ejercicio1.libreria_web.entidades.Editorial;
import com.ejercicio1.libreria_web.entidades.Libro;
import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import com.ejercicio1.libreria_web.repositorios.AutorRepositorio;
import com.ejercicio1.libreria_web.repositorios.EditorialRepositorio;
import com.ejercicio1.libreria_web.repositorios.LibroRepositorio;
import com.ejercicio1.libreria_web.servicios.ServicioAutor;
import com.ejercicio1.libreria_web.servicios.ServicioEditorial;
import com.ejercicio1.libreria_web.servicios.ServicioLibro;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author USER
 */
@Controller
@RequestMapping("/libro")
public class CrudController {

    @Autowired
    private ServicioLibro servicioLibro;
    @Autowired
    private ServicioAutor servicioAutor;
    @Autowired
    private ServicioEditorial servicioEditorial;
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    //CRUD para libros
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/alta/{id}")
    public String alta(ModelMap modelo, @PathVariable String id) {
        try {
            servicioLibro.darAlta(id);
            List<Libro> libros = servicioLibro.listarLibros();
            modelo.addAttribute("libros", libros);
            return "redirect:/libros";
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/baja/{id}")
    public String baja(ModelMap modelo, @PathVariable String id) {
        try {
            servicioLibro.darBaja(id);
            List<Libro> libros = servicioLibro.listarLibros();
            modelo.addAttribute("libros", libros);
            return "redirect:/libros";
        } catch (Exception e) {
            throw e;
        }
    }

    /*La path variable es una variable que viaja a traves de la url*/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        try {
            //busco el objeto libro por el id traido desde la url
            //luego lo inyecto con el modelmap modelo  
            List<Autor> autores = autorRepositorio.findAll();
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);
            modelo.put("libro", servicioLibro.buscarLibroPorId(id));
            return "libro-modificar.html";
        } catch (Exception e) {
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @PostMapping("/modificado/{id}")
    public String modificado(@PathVariable String id, ModelMap modelo, @RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) String isbn, @RequestParam(required = false) String nombre, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Integer ejemplaresRestantes, @RequestParam(required = false) Integer ejemplaresPrestados,
            @RequestParam(required = false) String idAutor, @RequestParam(required = false) String idEditorial) {
        try {
            List<Autor> autores = autorRepositorio.findAll();
            List<Editorial> editoriales = editorialRepositorio.findAll();
            List<Libro> libros = servicioLibro.listarLibros();
            modelo.addAttribute("libros", libros);
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);

            Autor autor = servicioAutor.buscarPorId(idAutor);
            Editorial editorial = servicioEditorial.buscarEditorialPorId(idEditorial);
            servicioLibro.modificarLibro(archivo, id, nombre, anio, ejemplares, ejemplaresRestantes, ejemplaresPrestados, true, autor, editorial);
            return "redirect:/libros";
        } catch (MiExcepcion e) {
            return "libros.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(ModelMap modelo, @PathVariable String id) throws Exception {
        try {
            servicioLibro.eliminarLibro(id);
            List<Libro> libros = servicioLibro.listarLibros();
            modelo.addAttribute("libros", libros);
            return "redirect:/libros";
        } catch (MiExcepcion e) {
            throw e;
        }
    }
}
