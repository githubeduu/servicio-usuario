package com.example.serviciousuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.serviciousuario.model.Auth;
import com.example.serviciousuario.repository.AuthRepository;

@Service
public class AuthServicelmpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Auth validatePassword(String username, String password) {
        return authRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Auth createAuth(Auth auth){
        return authRepository.save(auth);
    }
}
