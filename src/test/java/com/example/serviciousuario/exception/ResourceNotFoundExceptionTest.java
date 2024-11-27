package com.example.serviciousuario.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        // Arrange
        String mensaje = "Recurso no encontrado";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(mensaje);

        // Assert
        assertEquals(mensaje, exception.getMessage());
    }

    @Test
    void testExceptionWithoutMessage() {
        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(null);

        // Assert
        assertNull(exception.getMessage());
    }
}
