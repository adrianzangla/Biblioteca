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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    private final SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/registro")
    public String registrar(ModelMap modelo) {
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
}