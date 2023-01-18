/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.ServicioExcepcion;
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
public class LibroServicio {

    @Autowired
    LibroRepositorio libroRepositorio;
    @Autowired
    AutorServicio autorServicio;
    @Autowired
    EditorialServicio editorialServicio;

    private void validar(String id) throws ServicioExcepcion {
        if (id == null || id.trim().isEmpty()) {
            throw new ServicioExcepcion("Libro inválido");
        }
    }

    private void validar(Integer ejemplares,
            Integer ejemplaresPrestados) throws ServicioExcepcion {

        if (ejemplares == null || ejemplares < 0) {
            throw new ServicioExcepcion("Ejemplares inválidos");
        }
        if (ejemplaresPrestados == null
                || ejemplaresPrestados < 0
                || ejemplaresPrestados > ejemplares) {
            throw new ServicioExcepcion("Ejemplares Prestados inválidos");
        }
    }

    private void validar(Long isbn,
            String titulo,
            Integer anio) throws ServicioExcepcion {
        if (isbn == null || isbn < 0) {
            throw new ServicioExcepcion("ISBN inválido");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ServicioExcepcion("Título inválido");
        }
        if (anio == null || anio < 0) {
            throw new ServicioExcepcion("Año inválido");
        }
    }

    public Libro leer(String id) throws ServicioExcepcion {
        validar(id);
        Libro libro = libroRepositorio.econtrarAutorPorIdActivo(id);
        if (libro == null) {
            throw new ServicioExcepcion("Libro inválido");
        }
        return libro;
    }

    public List<Libro> leer() {
        return libroRepositorio.encontrarTodosLibroActivo();
    }

    @Transactional
    public void crear(Long isbn,
            String titulo,
            Integer anio,
            Integer ejemplares,
            Integer ejemplaresPrestados,
            String idAutor,
            String idEditorial) throws ServicioExcepcion {
        validar(isbn, titulo, anio);
        validar(ejemplares, ejemplaresPrestados);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAlta(true);
        libro.setAutor(autorServicio.leer(idAutor));
        libro.setEditorial(editorialServicio.leer(idEditorial));
        libroRepositorio.save(libro);
    }

    @Transactional
    public void actualizar(String id,
            Long isbn,
            String titulo,
            Integer anio,
            Integer ejemplares,
            Integer ejemplaresPrestados,
            String idAutor,
            String idEditorial) throws ServicioExcepcion {
        validar(isbn, titulo, anio);
        validar(ejemplares, ejemplaresPrestados);
        Libro libro = leer(id);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        libro.setAutor(autorServicio.leer(idAutor));
        libro.setEditorial(editorialServicio.leer(idEditorial));
        libroRepositorio.save(libro);
    }

    @Transactional
    public Libro prestar(String id) throws ServicioExcepcion {
        Libro libro = leer(id);
        if (libro.getEjemplaresRestantes() < 1) {
            throw new ServicioExcepcion("No quedan más libros");
        }
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+1);
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        libroRepositorio.save(libro);
        return libro;
    }

    @Transactional
    public void borrar(String id) throws ServicioExcepcion {
        Libro libro = leer(id);
        libro.setAlta(false);
        libroRepositorio.save(libro);
    }
}
