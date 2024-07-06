package com.example.serviciousuario.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.serviciousuario.model.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    @Query("SELECT p FROM Productos p WHERE p.categoriaid = :categoriaId")
    List<Productos> findByCategoriaId(Long categoriaId);
} 

