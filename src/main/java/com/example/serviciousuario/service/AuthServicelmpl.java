package com.example.serviciousuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.serviciousuario.model.Auth;
import com.example.serviciousuario.repository.AuthRepository;

@Service
public class AuthServicelmpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public boolean validatePassword(String username, String password) {
     
        Auth auth = authRepository.findByUsernameAndPassword(username, password);
        
        return auth != null;
    }

    @Override
    public Auth createAuth(Auth auth){
        return authRepository.save(auth);
    }
}
