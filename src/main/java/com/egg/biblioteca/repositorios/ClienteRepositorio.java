/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Cliente;
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
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
    @Query("SELECT c FROM Cliente c WHERE c.alta = true")
    public List<Cliente> encontrarTodosAltaTrue();
    
    @Query("SELECT c FROM Cliente c WHERE c.id = :id AND c.alta = true")
    public Cliente encontrarPorIdAltaTrue(@Param(value = "id") String id);
}
