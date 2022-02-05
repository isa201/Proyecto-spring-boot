/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.repositorios;

import com.ejercicio1.libreria_web.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    /*
    Los repositorios se usan para hacer consultar a la base de datos
    Es como el DAO
    Se pueden usar en los servicios estos metodos propios creados o los metodos que heredan
    JpaRepository como findById,findAll,etc.
     */
    @Query("SELECT l FROM Libro l WHERE l.isbn= :isbn ")
    public Libro buscarLibroPorIsbn(@Param("isbn") long isbn);

    @Query("SELECT l FROM Libro l WHERE l.id= :id ")
    public Libro buscarLibroPorId(@Param("id") String id);
}
