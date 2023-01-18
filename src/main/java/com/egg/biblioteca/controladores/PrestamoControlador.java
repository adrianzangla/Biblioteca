/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.ClienteServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import com.egg.biblioteca.servicios.PrestamoServicio;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        modelo.put("libros", libroServicio.leer());
        modelo.put("clientes", clienteServicio.leer());
        return "formulario-prestamo";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo,
            @RequestParam String fechaDevolucion,
            @RequestParam String idLibro,
            @RequestParam String idCliente) {
        try {
            prestamoServicio.crear(Date.valueOf(fechaDevolucion), idLibro, idCliente);
            modelo.put("exito", "Prestamo registrado");
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
        }
        return "formulario-prestamo";
    }
}
