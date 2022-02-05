/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.repositorios;

import com.ejercicio1.libreria_web.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author USER
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.id= :id ")
    public Usuario buscarUsuarioPorId(@Param("id") String id);

    @Query("SELECT u FROM Usuario u WHERE u.mail= :mail ")
    public Usuario buscarUsuarioPorMail(@Param("mail") String mail);
}
