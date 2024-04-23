package com.example.serviciousuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.serviciousuario.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    
} 

