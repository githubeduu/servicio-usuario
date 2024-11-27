package com.example.serviciousuario.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.example.serviciousuario.DTO.CreacionUsuarioDTO;
import com.example.serviciousuario.DTO.LoginDTO;
import com.example.serviciousuario.DTO.UserResponse;
import com.example.serviciousuario.model.Auth;
import com.example.serviciousuario.model.Roles;
import com.example.serviciousuario.model.Usuario;
import com.example.serviciousuario.service.AuthService;
import com.example.serviciousuario.service.RolesService;
import com.example.serviciousuario.service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    public void testCreateUsuario() throws Exception {
        Roles mockRole = new Roles();
        mockRole.setId(1L);
        mockRole.setRol("Admin");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");

        when(rolesServiceMock.getRolesById(1L)).thenReturn(Optional.of(mockRole));
        when(usuarioServiceMock.createUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "nombre": "Juan perez",
                            "rut": "12345678-9",
                            "direccion": "san pablo 3900",
                            "comuna": "santiago",
                            "rolId": 1,
                            "username": "jperez",
                            "password": "123456"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    public void testUpdateUsuario() throws Exception {
        // Crear un rol simulado
        Roles mockRol = new Roles();
        mockRol.setId(1L);
        mockRol.setRol("Admin");

        // Usuario existente
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(123L);
        usuarioExistente.setDireccion("Antigua dirección");
        usuarioExistente.setComuna("Antigua comuna");
        usuarioExistente.setRol(mockRol);

        // Usuario actualizado
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(123L);
        usuarioActualizado.setDireccion("San pablo 39000");
        usuarioActualizado.setComuna("Quinta normal");
        usuarioActualizado.setRol(mockRol);

        // Configurar los mocks
        when(usuarioServiceMock.updateUsuario(eq(123L), any(Usuario.class))).thenReturn(usuarioActualizado);

        // Ejecutar el test
        mockMvc.perform(put("/usuario/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "direccion": "San pablo 39000",
                            "comuna": "Quinta normal",
                            "rolId": 1
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("San pablo 39000"))
                .andExpect(jsonPath("$.comuna").value("Quinta normal"))
                .andExpect(jsonPath("$.roles.id").value(1))
                .andExpect(jsonPath("$.roles.rol").value("Admin"));
    }



    @Test
    public void testDeleteUsuario() throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("admin");

        when(authServiceMock.validateToken("Bearer valid-token")).thenReturn(userResponse);

        mockMvc.perform(delete("/usuario/1")
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario eliminado correctamente"));

        verify(usuarioServiceMock).deleteUsuario(1L);
    }

    @Test
    public void testLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("johndoe");
        loginDTO.setPassword("password");

        Auth auth = new Auth();
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        auth.setUsuario(usuario);

        when(authServiceMock.validatePassword("johndoe", "password")).thenReturn(auth);
        when(usuarioServiceMock.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/usuario/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "johndoe",
                            "password": "password"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testLoginInvalidCredentials() throws Exception {
        when(authServiceMock.validatePassword("johndoe", "wrongpassword")).thenReturn(null);

        mockMvc.perform(post("/usuario/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "johndoe",
                            "password": "wrongpassword"
                        }
                        """))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Contraseña incorrecta"));
    }
}

