/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Cliente;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.entidades.Prestamo;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.repositorios.PrestamoRepositorio;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adria
 */
@Service
public class PrestamoServicio {
    
    @Autowired
    PrestamoRepositorio prestamoRepositorio;
    
    @Autowired
    LibroServicio libroServicio;
    
    @Autowired
    ClienteServicio clienteServicio;
    
    private void validar(Date fechaDevolucion,
            String idLibro,
            String idCliente) throws ServicioExcepcion {
        if (fechaDevolucion == null) {
            throw new ServicioExcepcion("Fecha inválida");
        }
        if (idLibro == null || idLibro.trim().isEmpty()) {
            throw new ServicioExcepcion("Libro inválido");
        }
        if (idCliente == null || idCliente.trim().isEmpty()) {
            throw new ServicioExcepcion("Cliente inválido");
        }
    }
    
    @Transactional
    public void crear(Date fechaDevolucion,
            String idLibro,
            String idCliente) throws ServicioExcepcion {
        validar(fechaDevolucion, idLibro, idCliente);
        Date fechaPrestamo = new Date();
        if (fechaPrestamo.after(fechaDevolucion)) {
            throw new ServicioExcepcion("Fecha inválida");
        }
        Libro libro = libroServicio.prestar(idLibro);
        Cliente cliente = clienteServicio.leer(idCliente);
        if (cliente == null) {
            throw new ServicioExcepcion("Cliente inválido");
        }
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setAlta(true);
        prestamo.setLibro(libro);
        prestamo.setCliente(cliente);
        prestamoRepositorio.save(prestamo);
    }
}
