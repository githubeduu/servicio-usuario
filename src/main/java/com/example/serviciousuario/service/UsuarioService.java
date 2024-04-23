package com.example.serviciousuario.service;

import java.util.List;
import java.util.Optional;

import com.example.serviciousuario.model.Usuario;

public interface UsuarioService {
    List<Usuario> getAllUsuario();
    Optional<Usuario> getUsuarioById(Long id);
    Usuario createUsuario(Usuario usuario); 
    Usuario updateUsuario(Long id, Usuario usuario);
    void deleteUsuario(Long id);
}
