package com.example.serviciousuario.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.serviciousuario.model.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreacionUsuarioDTOTest {

    private CreacionUsuarioDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CreacionUsuarioDTO();
    }

    @Test
    void testGettersAndSetters() {
        // Datos de prueba
        Long id = 1L;
        String nombre = "Juan Perez";
        String rut = "12345678-9";
        String direccion = "Calle Falsa 123";
        String comuna = "Santiago";
        Long rolId = 2L;
        String username = "juanperez";
        String password = "password123";
        Roles rol = new Roles();
        rol.setId(rolId);
        rol.setRol("ADMIN");

        // Setters
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setRut(rut);
        dto.setDireccion(direccion);
        dto.setComuna(comuna);
        dto.setRolId(rolId);
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setRol(rol);

        // Getters
        assertEquals(id, dto.getId());
        assertEquals(nombre, dto.getNombre());
        assertEquals(rut, dto.getRut());
        assertEquals(direccion, dto.getDireccion());
        assertEquals(comuna, dto.getComuna());
        assertEquals(rolId, dto.getRolId());
        assertEquals(username, dto.getUsername());
        assertEquals(password, dto.getPassword());
        assertEquals(rol, dto.getRol());
    }

    @Test
    void testDefaultValues() {
        // Verificar que los valores iniciales sean nulos
        assertNull(dto.getId());
        assertNull(dto.getNombre());
        assertNull(dto.getRut());
        assertNull(dto.getDireccion());
        assertNull(dto.getComuna());
        assertNull(dto.getRolId());
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
        assertNull(dto.getRol());
    }
}
