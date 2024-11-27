package com.example.serviciousuario.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginDTOTest {

    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        loginDTO = new LoginDTO();
    }

    @Test
    void testGettersAndSetters() {
        // Datos de prueba
        String username = "testuser";
        String password = "password123";

        // Setters
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);

        // Getters
        assertEquals(username, loginDTO.getUsername());
        assertEquals(password, loginDTO.getPassword());
    }

    @Test
    void testDefaultValues() {
        // Verificar que los valores iniciales sean nulos
        assertNull(loginDTO.getUsername());
        assertNull(loginDTO.getPassword());
    }
}
