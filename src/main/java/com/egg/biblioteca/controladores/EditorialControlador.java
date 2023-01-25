/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registro")
    public String registrar() {
        return "formulario-editorial.html";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.crear(nombre);
            modelo.put("exito", "Editorial registrada");
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return "formulario-editorial.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("editoriales", editorialServicio.leer());
        return "lista-editoriales.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("editorial", editorialServicio.leer(id));
            return "editar-editorial.html";
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "lista-editoriales.html";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable String id,
            ModelMap modelo,
            @RequestParam String nombre) {
        try {
            editorialServicio.actualizar(id, nombre);
            modelo.put("exito", "Editorial editada");
        } catch (ServicioExcepcion ex) {
            modelo.put("error", ex.getMessage());
        } finally {
            return editar(id, modelo);
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) {
        try {
            editorialServicio.borrar(id);
        } catch (ServicioExcepcion ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/#editoriales";

    }

}
