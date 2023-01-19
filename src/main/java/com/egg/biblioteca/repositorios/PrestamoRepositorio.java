/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Prestamo;
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
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
    
    @Query("SELECT p FROM Prestamo p WHERE p.alta = true")
    public List<Prestamo> encontrarTodosAltaTrue();
    
    @Query("SELECT p FROM Prestamo p WHERE p.id = :id AND p.alta = true")
    public Prestamo encontrarPorIdAltaTrue(@Param(value = "id") String id);
    
}
