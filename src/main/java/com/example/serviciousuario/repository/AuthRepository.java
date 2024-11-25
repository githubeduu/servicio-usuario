package com.example.serviciousuario.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.serviciousuario.model.Auth;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByUsernameAndPassword(String username, String password);
    Auth findByUsuarioId(Long usuarioId);
}
