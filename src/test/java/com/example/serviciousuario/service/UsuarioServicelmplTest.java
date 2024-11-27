package com.example.serviciousuario.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.serviciousuario.model.Auth;
import com.example.serviciousuario.model.Roles;
import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.repository.AuthRepository;
import com.example.serviciousuario.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServicelmplTest {

    @InjectMocks
    private UsuarioServicelmpl usuarioServicio;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private RolesService rolesService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Sebastian Rubio");
        usuario.setRut("198857534");
        usuario.setDireccion("Rio Boroa 1744");
        usuario.setComuna("Cerro Navia");
        usuario.setRolId(1L);
    }

    @Test
    void testGetAllUsuario() {
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Maria Lopez");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, usuario2));

        List<Usuario> usuarios = usuarioServicio.getAllUsuario();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testGetUsuarioById_Found() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioServicio.getUsuarioById(1L);

        assertTrue(result.isPresent());
        assertEquals("Sebastian Rubio", result.get().getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUsuarioById_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioServicio.getUsuarioById(1L);

        assertFalse(result.isPresent());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUsuario() {
        // Datos de prueba
        Long usuarioId = 1L;
        Long rolId = 2L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setDireccion("Dirección Original");
        usuarioExistente.setComuna("Comuna Original");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setRolId(rolId);
        usuarioActualizado.setDireccion("Nueva Dirección");
        usuarioActualizado.setComuna("Nueva Comuna");

        Roles rol = new Roles();
        rol.setId(rolId);
        rol.setRol("Nuevo Rol");

        // Simulación de comportamientos
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        when(rolesService.getRolesById(rolId)).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Llamada al método
        Usuario resultado = usuarioServicio.updateUsuario(usuarioId, usuarioActualizado);

        // Verificaciones
        assertEquals("Nueva Dirección", resultado.getDireccion());
        assertEquals("Nueva Comuna", resultado.getComuna());
        assertEquals(rol, resultado.getRoles()); // Verifica que el rol fue asignado
        verify(usuarioRepository, times(1)).save(usuarioExistente);
    }



    @Test
    void testUpdateUsuario_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setDireccion("Nueva Direccion");

        Usuario result = usuarioServicio.updateUsuario(1L, usuarioActualizado);

        assertNull(result);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testDeleteUsuario() {
        // Configuración
        Long usuarioId = 1L;

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(usuarioId);

        Auth authMock = new Auth();
        authMock.setId(10L);
        authMock.setUsuario(usuarioMock);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));
        when(authRepository.findByUsuarioId(usuarioId)).thenReturn(authMock);

        // Acción
        usuarioServicio.deleteUsuario(usuarioId);

        // Verificación
        verify(authRepository, times(1)).delete(authMock);
        verify(usuarioRepository, times(1)).deleteById(usuarioId);
    }


    @Test
    void testDeleteUsuario_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioServicio.deleteUsuario(1L);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, never()).deleteById(any());
    }
}
