/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adria
 */
@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Autowired
    LibroRepositorio libroRepositorio;
    
    private void validarId(String id) throws ServicioExcepcion {
        if (id == null) {
            throw new ServicioExcepcion("Editorial inválida");
        }
    }
    
    private void validar(String nombre) throws ServicioExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ServicioExcepcion("Nombre inválido");
        }
    }
    
    public Editorial leer(String id) throws ServicioExcepcion {
        validarId(id);
        Editorial editorial = editorialRepositorio.encontrarEditorialPorIdActiva(id);
        if (editorial == null) {
            throw new ServicioExcepcion("Editorial inválida");
        }
        return editorial;
    }
    
    public List<Editorial> leer() {
        return editorialRepositorio.encontrarTodasEditorialActiva();
    }
    
    @Transactional
    public void crear(String nombre) throws ServicioExcepcion {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public void actualizar(String id, String nombre) throws ServicioExcepcion {
        validar(id);
        validar(nombre);
        Editorial editorial = leer(id);
        if (editorial == null) {
            throw new ServicioExcepcion("Editorial inválida");
        }
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public void borrar(String id) throws ServicioExcepcion {
        validar(id);
        Editorial editorial = leer(id);
        if (editorial == null) {
            throw new ServicioExcepcion("Editorial inválida");
        }
        editorial.setAlta(false);
        List<Libro> libros = libroRepositorio.encontrarPorEditorial(id);
        for (Libro libro : libros) {
            libro.setEditorial(null);
        }
        libroRepositorio.saveAll(libros);
        editorialRepositorio.save(editorial);
    }
}
