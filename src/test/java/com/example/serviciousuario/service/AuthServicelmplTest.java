package com.example.serviciousuario.service;

import com.example.serviciousuario.DTO.UserResponse;
import com.example.serviciousuario.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServicelmplTest {

    @InjectMocks
    private AuthServicelmpl authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // void testValidateToken_Success() {
    //     String token = "Bearer valid-token";
    //     String url = "http://host.docker.internal:8085/auth/validate";

    //     UserResponse mockResponse = new UserResponse();
    //     mockResponse.setUsername("testuser");
    //     mockResponse.setAuthorities(List.of("ROLE_USER"));

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("Authorization", token);
    //     HttpEntity<String> entity = new HttpEntity<>(headers);

    //     when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(UserResponse.class)))
    //             .thenReturn(ResponseEntity.ok(mockResponse));

    //     UserResponse result = authService.validateToken(token);

    //     assertNotNull(result);
    //     assertEquals("testuser", result.getUsername());
    //     assertTrue(result.getAuthorities().contains("ROLE_USER"));
    //     verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(UserResponse.class));
    // }

    // @Test
    // void testValidateToken_InvalidToken() {
    //     String token = "Bearer invalid-token";
    //     String url = "http://host.docker.internal:8085/auth/validate";

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("Authorization", token);
    //     HttpEntity<String> entity = new HttpEntity<>(headers);

    //     when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(UserResponse.class)))
    //             .thenThrow(new RuntimeException("Unauthorized"));

    //     UserResponse result = authService.validateToken(token);

    //     assertNull(result);
    //     verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(UserResponse.class));
    // }
}
