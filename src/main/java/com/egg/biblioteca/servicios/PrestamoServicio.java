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
import java.util.List;
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

    private void validar(Date fechaPrestamo,
            Date fechaDevolucion) throws ServicioExcepcion {
        if (fechaPrestamo == null) {
            throw new ServicioExcepcion("Fecha de préstamo inválida");
        }
        if (fechaDevolucion == null || fechaDevolucion.before(fechaPrestamo)) {
            throw new ServicioExcepcion("Fecha de devolución inválida");
        }
    }

    private void validar(String id) throws ServicioExcepcion {
        if (id == null || id.trim().isEmpty()) {
            throw new ServicioExcepcion("Préstamo inválido");
        }
    }

    @Transactional
    public void crear(Date fechaPrestamo,
            Date fechaDevolucion,
            String idLibro,
            String idCliente) throws ServicioExcepcion {

        validar(fechaPrestamo, fechaDevolucion);

        Libro libro = libroServicio.prestar(idLibro);

        Cliente cliente = clienteServicio.leer(idCliente);

        Prestamo prestamo = new Prestamo();

        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);

        prestamo.setAlta(true);

        prestamo.setLibro(libro);
        prestamo.setCliente(cliente);

        prestamoRepositorio.save(prestamo);
    }

    public List<Prestamo> leer() {
        return prestamoRepositorio.encontrarTodosAltaTrue();
    }

    public Prestamo leer(String id) throws ServicioExcepcion {
        validar(id);
        Prestamo prestamo = prestamoRepositorio.encontrarPorIdAltaTrue(id);
        if (prestamo == null) {
            throw new ServicioExcepcion("Préstamo inválido");
        }
        return prestamo;
    }

    public void actualizar(String id,
            Date fechaDevolucion) throws ServicioExcepcion {
        Prestamo prestamo = leer(id);
        validar(prestamo.getFechaPrestamo(), fechaDevolucion);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamoRepositorio.save(prestamo);
    }

    public void borrar(String id) throws ServicioExcepcion {
        Prestamo prestamo = leer(id);
        prestamo.setAlta(false);
        prestamoRepositorio.save(prestamo);
        libroServicio.devolver(prestamo.getLibro().getId());
    }
}
