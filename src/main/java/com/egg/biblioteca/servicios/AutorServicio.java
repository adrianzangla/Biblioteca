/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
import com.egg.biblioteca.repositorios.AutorRepositorio;
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
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;
    
    @Autowired
    LibroRepositorio libroRepositorio;
    
    private void validarId(String id) throws ServicioExcepcion {
        if (id == null) {
            throw new ServicioExcepcion("Autor inválido");
        }
    }
    
    private void validar(String nombre) throws ServicioExcepcion {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ServicioExcepcion("Nombre inválido");
        }
    }
    
    public Autor leer(String id) throws ServicioExcepcion {
        validarId(id);
        Autor autor = autorRepositorio.encontrarAutorPorIdActivo(id);
        if (autor == null) {
            throw new ServicioExcepcion("Libro inválido");
        }
        return autor;
    }
    
    public List<Autor> leer() {
        return autorRepositorio.encontrarTodosAutorActivo();
    }
    
    @Transactional
    public void crear(String nombre) throws ServicioExcepcion {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }
    
    @Transactional
    public void actualizar(String id, String nombre) throws ServicioExcepcion {
        validar(nombre);
        Autor autor = leer(id);
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }
    
    @Transactional
    public void borrar(String id) throws ServicioExcepcion {
        Autor autor = leer(id);
        autor.setAlta(false);
        List<Libro> libros = libroRepositorio.encontrarPorAutor(id);
        for (Libro libro : libros) {
            libro.setAutor(null);
        }
        libroRepositorio.saveAll(libros);
        autorRepositorio.save(autor);
    }
}
