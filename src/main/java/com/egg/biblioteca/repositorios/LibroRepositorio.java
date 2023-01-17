/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author adria
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("Select l FROM Libro l WHERE l.id = :id AND l.alta = true")
    public Libro econtrarAutorPorIdActivo(@Param(value = "id") String id);
    
    @Query("SELECT l FROM Libro l WHERE l.alta = true")
    public List<Libro> encontrarTodosLibroActivo();
    
    @Query("SELECT l FROM Libro l WHERE l.autor.id = :id")
    public List<Libro> encontrarPorAutor(@Param(value = "id") String id);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial.id = :id")
    public List<Libro> encontrarPorEditorial(@Param(value = "id") String id);
}
