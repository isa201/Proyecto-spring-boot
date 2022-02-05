/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio1.libreria_web.servicios;

import com.ejercicio1.libreria_web.entidades.Imagen;
import com.ejercicio1.libreria_web.repositorios.ImagenRespositorio;
import com.ejercicio1.libreria_web.excepcion.MiExcepcion;
import java.io.IOException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author USER
 */
@Service
public class ServicioImagen {

    @Autowired
    private ImagenRespositorio ir;

    //Este metodo recibe el archivo que se va guardar como imagen
    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiExcepcion, IOException {
        if (archivo != null) {
            try {
                //creo una nueva imagen y seteo los valor del archivo
                //los metodos de archivo salen de la clase multipartfile
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return ir.save(imagen);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Imagen actualizar(String id, MultipartFile archivo) {
        if (archivo != null) {
            try {
                if (id != null) {
                    Imagen imagen = new Imagen();

                    Optional<Imagen> respuesta = ir.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }

                    imagen.setMime(archivo.getContentType());
                    imagen.setNombre(archivo.getName());
                    imagen.setContenido(archivo.getBytes());

                    return ir.save(imagen);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
