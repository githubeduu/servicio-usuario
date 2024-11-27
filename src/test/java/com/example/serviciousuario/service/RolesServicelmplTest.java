package com.example.serviciousuario.service;

import com.example.serviciousuario.model.Roles;
import com.example.serviciousuario.repository.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolesServicelmplTest {

    @InjectMocks
    private RolesServicelmpl rolesService;

    @Mock
    private RolesRepository rolesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        Roles role1 = new Roles();
        role1.setId(1L);
        role1.setRol("Admin");

        Roles role2 = new Roles();
        role2.setId(2L);
        role2.setRol("User");

        when(rolesRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        // Act
        List<Roles> roles = rolesService.getAllRoles();

        // Assert
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertEquals("Admin", roles.get(0).getRol());
        assertEquals("User", roles.get(1).getRol());
        verify(rolesRepository, times(1)).findAll();
    }

    @Test
    void testGetRolesById_Found() {
        // Arrange
        Roles role = new Roles();
        role.setId(1L);
        role.setRol("Admin");

        when(rolesRepository.findById(1L)).thenReturn(Optional.of(role));

        // Act
        Optional<Roles> result = rolesService.getRolesById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Admin", result.get().getRol());
        verify(rolesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRolesById_NotFound() {
        // Arrange
        when(rolesRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Roles> result = rolesService.getRolesById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(rolesRepository, times(1)).findById(1L);
    }
}
