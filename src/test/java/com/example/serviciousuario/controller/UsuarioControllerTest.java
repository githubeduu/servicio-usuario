package com.example.serviciousuario.controller;
/*
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UsuarioControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServicelmpl usuarioServiceMock;


    @Test
    public void obtenerTodosTest() throws Exception {
        
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Andrea Miranda");
        usuario1.setRut("12345678-9");
        usuario1.setDireccion("Rio boroa 1744");
        usuario1.setComuna("Cerro navia");
        usuario1.setRol(null);       

        Usuario usuario2 = new Usuario();
        usuario1.setId(2L);
        usuario1.setNombre("Juan Perez");
        usuario1.setRut("19876543-9");
        usuario1.setDireccion("Rio boroa 1746");
        usuario1.setComuna("Cerro navia");
        usuario1.setRol(null);
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioServiceMock.getAllUsuario()).thenReturn(usuarios);

        log.info("Testing obtenerTodosTest: starting test with {} usuarios", usuarios.size());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", Matchers.is("Andrea Miranda")))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre", Matchers.is("Jose Pablo")));

        log.info("Testing obtenerTodosTest: test completed successfully");
    }
}
*/
