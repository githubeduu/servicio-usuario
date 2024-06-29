package com.example.serviciousuario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.serviciousuario.model.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    
} 

