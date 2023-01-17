/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Cliente;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.repositorios.ClienteRepositorio;
import java.util.List;
import javax.transaction.Transactional;
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
                || Long.valueOf(telefono) < 0) {
            throw new ServicioExcepcion("Teléfono inválido");
        }
    }
    
    private void validar(String id) throws ServicioExcepcion {
        if (id == null || id.trim().isEmpty()) {
            throw new ServicioExcepcion("Cliente inválido");
        }
    }
    
    @Transactional
    public void crear(Long documento,
            String nombre,
            String apellido,
            String telefono) throws ServicioExcepcion {

        validar(documento, nombre, apellido, telefono);
        
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
        
        clienteRepositorio.save(cliente);
        
    }
    
    public List<Cliente> leer() {
        return clienteRepositorio.encontrarTodosAltaTrue();
    }
    
    public Cliente leer(String id) throws ServicioExcepcion {
        validar(id);
        return clienteRepositorio.encontrarPorIdAltaTrue(id);
    }
    
    @Transactional
    public void actualizar(String id,
            Long documento,
            String nombre,
            String apellido,
            String telefono) throws ServicioExcepcion {
        validar(id);
        validar(documento, nombre, apellido, telefono);
        Cliente cliente = clienteRepositorio.encontrarPorIdAltaTrue(id);
        if (cliente == null) {
            throw new ServicioExcepcion("Cliente inválido");
        }
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        clienteRepositorio.save(cliente);
    }
    
    @Transactional
    public void borrar(String id) throws ServicioExcepcion {
        validar(id);
        Cliente cliente = clienteRepositorio.encontrarPorIdAltaTrue(id);
        if (cliente == null) {
            throw new ServicioExcepcion("Cliente inválido");
        }
        cliente.setAlta(false);
        clienteRepositorio.save(cliente);
    }
}
