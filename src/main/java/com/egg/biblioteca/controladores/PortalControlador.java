/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Cliente;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.entidades.Prestamo;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.ClienteServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import com.egg.biblioteca.servicios.PrestamoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author adria
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    LibroServicio libroServicio;
    
    @Autowired
    AutorServicio autorServicio;
    
    @Autowired
    EditorialServicio editorialServicio;
    
    @Autowired
    ClienteServicio clienteServicio;
    
    @Autowired
    PrestamoServicio prestamoServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        
        List<Libro> libros = libroServicio.leer();
        modelo.addAttribute("libros", libros);
        
        List<Autor> autores = autorServicio.leer();
        modelo.addAttribute("autores", autores);
        
        List<Editorial> editoriales = editorialServicio.leer();
        modelo.addAttribute("editoriales", editoriales);
        
        List<Cliente> clientes = clienteServicio.leer();
        modelo.addAttribute("clientes", clientes);
        System.out.println(clientes);
        
        List<Prestamo> prestamos = prestamoServicio.leer();
        modelo.addAttribute("prestamos", prestamos);
        
        return "index.html";
    }
}
