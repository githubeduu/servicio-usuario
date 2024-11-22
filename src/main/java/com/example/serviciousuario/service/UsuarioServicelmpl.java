package com.example.serviciousuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.serviciousuario.model.Auth;
import com.example.serviciousuario.model.Roles;
import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.repository.AuthRepository;
import com.example.serviciousuario.repository.UsuarioRepository;

@Service
public class UsuarioServicelmpl implements UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthRepository authRepository;
    
    @Autowired
    private RolesService rolesService;

    @Override
    public List<Usuario> getAllUsuario(){
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long id){
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario createUsuario(Usuario usuario){         
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuario) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            if (usuario.getRolId() != null) {
                Roles rol = rolesService.getRolesById(usuario.getRolId())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                usuarioExistente.setRol(rol);
            }

            if (usuario.getDireccion() != null) {
                usuarioExistente.setDireccion(usuario.getDireccion());
            }

            if (usuario.getComuna() != null) {
                usuarioExistente.setComuna(usuario.getComuna());
            }

            return usuarioRepository.save(usuarioExistente);
        }).orElse(null);
    }

   
    @Override
    public void deleteUsuario(Long id) {
        // Verificar si el usuario existe
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Eliminar el registro relacionado en Auth si existe
        Auth auth = authRepository.findByUsuarioId(id);
        if (auth != null) {
            authRepository.delete(auth);
        }

        // Eliminar el usuario
        usuarioRepository.deleteById(id);
    }
}
