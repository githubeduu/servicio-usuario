package com.example.serviciousuario.service;

import java.util.List;
import java.util.Optional;

import com.example.serviciousuario.model.Roles;

public interface RolesService {
    List<Roles> getAllRoles();
    Optional<Roles> getRolesById(Long id);
}
