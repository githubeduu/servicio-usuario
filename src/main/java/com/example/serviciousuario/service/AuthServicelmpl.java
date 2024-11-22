package com.example.serviciousuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.serviciousuario.DTO.UserResponse;
import com.example.serviciousuario.model.Auth;
import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.repository.AuthRepository;

@Service
public class AuthServicelmpl implements AuthService {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AuthRepository authRepository;

    @Override
    public Auth validatePassword(String username, String password) {
        return authRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Auth createAuth(Auth auth){
        return authRepository.save(auth);
    }

    @Override
    public UserResponse validateToken(String token) {
        try {
            String url = "http://localhost:8085/auth/validate";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<UserResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Log el error para más detalles
            System.out.println("Error en la validación del token: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        }
    }
    
}
