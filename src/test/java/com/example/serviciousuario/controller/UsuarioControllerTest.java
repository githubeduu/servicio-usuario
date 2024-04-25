package com.example.serviciousuario.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.service.UsuarioServicelmpl;


@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServicelmpl usuarioServiceMock;

    @Test
    public void obtenerTodosTest() throws Exception {

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Andrea Miranda");
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Jose Pablo");
        usuario2.setId(2L);
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioServiceMock.getAllUsuario()).thenReturn(usuarios);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", Matchers.is("Andrea Miranda"))) 
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre", Matchers.is("Jose Pablo")));
    }
}
