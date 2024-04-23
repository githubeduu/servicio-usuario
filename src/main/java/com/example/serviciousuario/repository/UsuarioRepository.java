package com.example.serviciousuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.serviciousuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
