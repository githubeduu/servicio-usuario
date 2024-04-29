package com.example.serviciousuario.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.service.AuthService;
import com.example.serviciousuario.service.RolesService;
import com.example.serviciousuario.service.UsuarioService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({UsuarioService.class, RolesService.class, AuthService.class})
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioServiceMock;

    @MockBean
    private RolesService rolesServiceMock;

    @MockBean
    private AuthService authServiceMock;

    @Test
    public void ObtenerTodosTest() throws Exception {
        
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Andrea Miranda");           

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Jose Pablo");
        List<Usuario> usuarios = List.of(usuario1, usuario2);
    
        when(usuarioServiceMock.getAllUsuario()).thenReturn(usuarios);

        mockMvc.perform(get("/usuario"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$._embedded.usuarioList[0].id").value(1))
         .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Andrea Miranda"))
         .andExpect(jsonPath("$._embedded.usuarioList[1].id").value(2))
         .andExpect(jsonPath("$._embedded.usuarioList[1].nombre").value("Jose Pablo"));

    }

    @Test
    public void ObtenerUsuarioError() throws Exception {
        Long userId = 1L;
        when(usuarioServiceMock.getUsuarioById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/" + userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

}

