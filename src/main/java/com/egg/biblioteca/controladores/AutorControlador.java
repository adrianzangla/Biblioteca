/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    AutorServicio autorServicio;

    @GetMapping("/registro")
    public String registro() {
        return "formulario-autor.html";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorServicio.crear(nombre);
            modelo.put("exito", "Autor registrado");
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return "formulario-autor.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("autores", autorServicio.leer());
        return "lista-autores.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("autor", autorServicio.leer(id));
            return "editar-autor.html";
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "lista-autores.html";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable String id,
            ModelMap modelo,
            @RequestParam String nombre) {
        try {
            autorServicio.actualizar(id, nombre);
            modelo.put("exito", "Autor editado");
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return editar(id, modelo);
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) {
        try {
            autorServicio.borrar(id);
            return "redirect:/autor/lista";
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return listar(modelo);
        }
    }
}
