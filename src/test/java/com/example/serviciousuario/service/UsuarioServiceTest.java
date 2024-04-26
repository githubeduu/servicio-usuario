package com.example.serviciousuario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioServicelmpl usuarioServicio;

    @Mock
    private UsuarioRepository usuarioRepositoryMock;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Sebastian Rubio");
        usuario.setRut("198857534");
        usuario.setDireccion("Rio boroa 1744");
        usuario.setComuna("Cerro navia");
    }

    @Test
    public void crearUsuarioTest(){

        when(usuarioRepositoryMock.save(any())).thenReturn(usuario);

        Usuario resultado = usuarioServicio.createUsuario(usuario);

        assertEquals("Sebastian Rubio", resultado.getNombre());
        assertEquals("198857534", resultado.getRut());
        assertEquals("Rio boroa 1744", resultado.getDireccion());
        assertEquals("Cerro navia", resultado.getComuna());

        verify(usuarioRepositoryMock).save(usuario);

    }

}
