package com.example.serviciousuario.DTO;

import java.util.List;

public class UserResponse {
    private String username;
    private List<String> authorities;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}