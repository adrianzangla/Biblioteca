/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author adria
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    LibroServicio libroServicio;
    @Autowired
    AutorServicio autorServicio;
    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registro")
    public String registrar(ModelMap modelo) {
        modelo.addAttribute("autores", autorServicio.leer());
        modelo.addAttribute("editoriales", editorialServicio.leer());
        return "formulario-libro.html";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo,
            @RequestParam(required = false) Long isbn,
            @RequestParam String titulo,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam(required = false) Integer ejemplaresPrestados,
            @RequestParam(required = false) Integer ejemplaresRestantes,
            @RequestParam String idAutor,
            @RequestParam String idEditorial) {
        try {
            libroServicio.crear(isbn,
                    titulo,
                    anio,
                    ejemplares,
                    ejemplaresPrestados,
                    ejemplaresRestantes,
                    idAutor,
                    idEditorial);
            modelo.put("exito", "Libro registrado");
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return registrar(modelo);
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("libros", libroServicio.leer());
        return "lista-libros.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("libro", libroServicio.leer(id));
            modelo.addAttribute("autores", autorServicio.leer());
            modelo.addAttribute("editoriales", editorialServicio.leer());
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return "editar-libro.html";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable String id,
            ModelMap modelo,
            @RequestParam String titulo,
            @RequestParam(required = false) Long isbn,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam(required = false) Integer ejemplaresPrestados,
            @RequestParam(required = false) Integer ejemplaresRestantes,
            @RequestParam String idAutor,
            @RequestParam String idEditorial) {
        try {
            libroServicio.actualizar(id,
                    isbn,
                    titulo,
                    anio,
                    ejemplares,
                    ejemplaresPrestados,
                    ejemplaresRestantes,
                    idAutor,
                    idEditorial);
            modelo.put("exito", "Libro editado");
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return editar(id, modelo);
        }
    }
    
    @GetMapping("borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) {
        try {
            libroServicio.borrar(id);
            return "redirect:/libro/lista";
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return listar(modelo);
        }
    }
}
