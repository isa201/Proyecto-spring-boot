package com.ejercicio1.libreria_web.excepcion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class MiExcepcion extends Exception {

    /**
     * Creates a new instance of <code>MiExcepcion</code> without detail
     * message.
     */
    public MiExcepcion() {
    }

    /**
     * Constructs an instance of <code>MiExcepcion</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public MiExcepcion(String msg) {
        super(msg);
    }
}
