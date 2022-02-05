/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.repositorios;

import com.ejercicio1.libreria_web.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author USER
 */
public interface ImagenRespositorio extends JpaRepository<Imagen, String> {
         
}
