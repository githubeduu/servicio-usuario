package com.example.serviciousuario.service;

import com.example.serviciousuario.model.Auth;

public interface AuthService {
    boolean validatePassword(String username, String password);
    Auth createAuth(Auth auth); 
}