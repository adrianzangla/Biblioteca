/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Cliente;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adria
 */
@Service
public class ClienteServicio {

    @Autowired
    ClienteRepositorio clienteRepositorio;

    private void validar(Long documento,
            String nombre,
            String apellido,
            String telefono) throws ServicioExcepcion {
        
        if (documento == null || documento < 0) {
            throw new ServicioExcepcion("Documento inválido");
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ServicioExcepcion("Nombre inválido");
        }
        
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ServicioExcepcion("Apellido inválido");
        }
        
        if (telefono == null 
                || telefono.trim().isEmpty() 
                || Integer.valueOf(telefono) < 0) {
            throw new ServicioExcepcion("Teléfono inválido");
        }
        
    }

    public void crear(Long documento,
            String nombre,
            String apellido,
            String telefono) throws ServicioExcepcion {

        validar(documento, nombre, apellido, telefono);
        
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
        
        clienteRepositorio.save(cliente);
        
    }
}
