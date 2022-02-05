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
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author USER
 */
@Controller
public class ControladorLibros {

    @Autowired
    private ServicioLibro servicioLibro;
    @Autowired
    private ServicioAutor servicioAutor;
    @Autowired
    private ServicioEditorial servicioEditorial;

    //usando el repositorio puedo traer todos los autores y editoriales en un arreglo y poder llenar el combo en el formulario
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/libros")
    public String libros(ModelMap modelo) {
        List<Libro> libros = servicioLibro.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libros.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @GetMapping("/libro-nuevo")
    public String libroNuevo(ModelMap modelo) {
        List<Autor> autores = autorRepositorio.findAll();
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        return "libro-nuevo.html";
    }

    //Dede el html usando thymeleaf le voy a pasar el id para despues usar el metodo de servicio que permite traer los objetos por sus id
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
    @PostMapping("/libro-nuevo")
    public String guardarLibro(ModelMap modelo, @RequestParam(required = false) MultipartFile archivo, @RequestParam(required = false) String isbn, @RequestParam(required = false) String nombre, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Integer ejemplaresRestantes, @RequestParam(required = false) Integer ejemplaresPrestados,
            @RequestParam(required = false) String idAutor, @RequestParam(required = false) String idEditorial) throws MiExcepcion {
        List<Autor> autores = autorRepositorio.findAll();
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        try {
            //con el metodo de cada servicio traigo el objeto por su id
            Autor autor = servicioAutor.buscarPorId(idAutor);
            Editorial editorial = servicioEditorial.buscarEditorialPorId(idEditorial);

            servicioLibro.registrarLibro(archivo, Long.parseLong(isbn), nombre, anio, ejemplares, ejemplaresRestantes, ejemplaresPrestados, true, autor, editorial);

            modelo.put("exito", "EXITO!!Libro registrado exitosamente!!");
            return "libro-nuevo.html";

        } catch (MiExcepcion | IOException | NumberFormatException e) {

            modelo.put("error", "ERROR!!El libro no ha podido ser registrado, vuelva a intentarlo");
            return "libro-nuevo.html";
        }
    }

   
    //model map sirve para agregar codigo al html desde el front 
    //se crea una variable y se le da el valor y en el html se accede desde el nombre de la variable creada
    //modelo.put("nombre_variable",valor);
    //con th:text="${exito}" muestro el valor de la variable en este caso el mensaje de exito o error
}
