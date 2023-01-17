package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Cliente;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.servicios.ClienteServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
        } finally {
            return "formulario-cliente.html";
        }
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Cliente> clientes = clienteServicio.leer();
        modelo.addAttribute("clientes", clientes);
        return "lista-clientes.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {
        try {
            modelo.put("cliente", clienteServicio.leer(id));
            return "editar-cliente";
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
            return "index.html";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable String id,
            ModelMap modelo,
            @RequestParam(required = false) Long documento,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String telefono) {
        System.out.println("Im in post method");
        try {
            clienteServicio.actualizar(id,
                    documento,
                    nombre,
                    apellido,
                    telefono);
            modelo.put("exito", "Cliente editado");
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
        } finally {
            return editar(id, modelo);
        }
    }
    
    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo){
        try {
            clienteServicio.borrar(id);
            return "redirect:/cliente/lista";
        } catch (ServicioExcepcion e) {
            modelo.put("error", e.getMessage());
            return listar(modelo);
        }
    }
}
