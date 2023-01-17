/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Editorial;
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
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    
    @Query("SELECT e FROM Editorial e WHERE e.id = :id AND e.alta = true")
    public Editorial encontrarEditorialPorIdActiva(@Param(value = "id")String id); 
    
    @Query("SELECT e FROM Editorial e WHERE e.alta = true")
    public List<Editorial> encontrarTodasEditorialActiva();
}
