package com.example.serviciousuario.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserResponseTest {

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
    }

    @Test
    void testGettersAndSetters() {
        // Datos de prueba
        String username = "testuser";
        List<String> authorities = List.of("ROLE_USER", "ROLE_ADMIN");

        // Setters
        userResponse.setUsername(username);
        userResponse.setAuthorities(authorities);

        // Getters
        assertEquals(username, userResponse.getUsername());
        assertEquals(authorities, userResponse.getAuthorities());
    }

    @Test
    void testDefaultValues() {
        // Verificar que los valores iniciales sean nulos
        assertNull(userResponse.getUsername());
        assertNull(userResponse.getAuthorities());
    }
}
