/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Prestamo;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.ClienteServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import com.egg.biblioteca.servicios.PrestamoServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author adria
 */
@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {

    @Autowired
    PrestamoServicio prestamoServicio;

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("/registro")
    public String registrar(ModelMap modelo) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        modelo.put("fechaPrestamo", formato.format(new Date()));
        modelo.put("libros", libroServicio.leer());
        modelo.put("clientes", clienteServicio.leer());
        return "formulario-prestamo.html";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo,
            @RequestParam String fechaPrestamo,
            @RequestParam String fechaDevolucion,
            @RequestParam String idLibro,
            @RequestParam String idCliente) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            prestamoServicio.crear(formato.parse(fechaPrestamo),
                    formato.parse(fechaDevolucion),
                    idLibro,
                    idCliente);
            modelo.put("exito", "Préstamo registrado");
        } catch (ServicioExcepcion e1) {
            modelo.put("error", e1.getMessage());
            return registrar(modelo);
        } catch (ParseException e2) {
            modelo.put("error", "Fecha inválida");
            return registrar(modelo);
        }
        return registrar(modelo);
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Prestamo prestamo = prestamoServicio.leer(id);
            String fechaPrestamo = formato.format(prestamo.getFechaPrestamo());
            String fechaDevolucion = formato.format(prestamo.getFechaDevolucion());
            modelo.put("prestamo", prestamo);
            modelo.put("fechaPrestamo", fechaPrestamo);
            modelo.put("fechaDevolucion", fechaDevolucion);
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
            return editar(id, modelo);
        }
        return "editar-prestamo.html";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable String id,
            ModelMap modelo,
            String fechaPrestamo,
            String fechaDevolucion,
            String idLibro,
            String idCliente) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            prestamoServicio.actualizar(id,
                    formato.parse(fechaDevolucion));
            modelo.put("exito", "Préstamo editado");
        } catch (ServicioExcepcion e1) {
            modelo.put("error", e1.getMessage());
        } catch (ParseException e2) {
            modelo.put("error", "Fecha inválida");
        } finally {
            return editar(id, modelo);
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable String id) {
        try {
            prestamoServicio.borrar(id);
        } catch (ServicioExcepcion e) {
            System.out.println(e.getMessage());
        } finally {
            return "redirect:/#prestamos";
        }
    }

}
