package com.example.serviciousuario.service;

import com.example.serviciousuario.model.Auth;

public interface AuthService {
    Auth validatePassword(String username, String password);
    Auth createAuth(Auth auth);
}