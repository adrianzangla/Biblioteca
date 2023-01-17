/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.excepciones;

/**
 *
 * @author adria
 */
public class ServicioExcepcion extends Exception {

    /**
     * Creates a new instance of <code>ServicioExcepcion</code> without detail
     * message.
     */
    public ServicioExcepcion() {
    }

    /**
     * Constructs an instance of <code>ServicioExcepcion</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServicioExcepcion(String msg) {
        super(msg);
    }
}
