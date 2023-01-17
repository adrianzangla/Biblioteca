package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("/registro")
    public String registrar() {
        return "formulario-cliente.html";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo,
            @RequestParam(required = false) Long documento,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String telefono) {
        try {
            clienteServicio.crear(documento, nombre, apellido, telefono);
            modelo.put("exito", "Cliente registrado");
            System.out.println("I got here!");
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
        } finally {
            return "formulario-cliente.html";
        }
    }
}
