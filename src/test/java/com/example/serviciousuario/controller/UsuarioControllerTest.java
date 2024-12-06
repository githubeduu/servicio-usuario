package com.example.serviciousuario.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
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

import jakarta.persistence.EntityNotFoundException;

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

      @Test
    public void testDeleteUsuarioUnauthorized() throws Exception {
        when(authServiceMock.validateToken("Bearer invalid-token")).thenReturn(null);
    
        mockMvc.perform(delete("/usuario/1")
                    .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Token inválido o expirado"));
    }

    @Test
    public void testDeleteUsuarioNotFound() throws Exception {
        when(authServiceMock.validateToken("Bearer valid-token")).thenReturn(new UserResponse());
        doThrow(new EntityNotFoundException("Usuario no encontrado")).when(usuarioServiceMock).deleteUsuario(1L);

        mockMvc.perform(delete("/usuario/1")
                    .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    public void testGetRoles() throws Exception {
        // Crear roles simulados
        Roles role1 = new Roles();
        role1.setId(1L);
        role1.setRol("administrador");

        Roles role2 = new Roles();
        role2.setId(2L);
        role2.setRol("usuario");

        // Configurar el mock del servicio
        when(rolesServiceMock.getAllRoles()).thenReturn(List.of(role1, role2));

        // Ejecutar la prueba
        mockMvc.perform(get("/usuario/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.rolesList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.rolesList[0].rol").value("administrador"))
                .andExpect(jsonPath("$._embedded.rolesList[0]._links.self.href").value("http://localhost/usuario/roles/1"))
                .andExpect(jsonPath("$._embedded.rolesList[1].id").value(2))
                .andExpect(jsonPath("$._embedded.rolesList[1].rol").value("usuario"))
                .andExpect(jsonPath("$._embedded.rolesList[1]._links.self.href").value("http://localhost/usuario/roles/2"))
                .andExpect(jsonPath("$._links.all-roles.href").value("http://localhost/usuario/roles"));
    }

    @Test
    public void testGetUsuarios() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Ana");

        when(usuarioServiceMock.getAllUsuario()).thenReturn(List.of(usuario1, usuario2));

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.usuarioList[1].id").value(2))
                .andExpect(jsonPath("$._links.usuarios.href").value("http://localhost/usuario"));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");

        when(usuarioServiceMock.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testGetUsuarioByIdNotFound() throws Exception {
        when(usuarioServiceMock.getUsuarioById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
public void testGetRolById() throws Exception {
    Roles rol = new Roles();
    rol.setId(1L);
    rol.setRol("Admin");

    when(rolesServiceMock.getRolesById(1L)).thenReturn(Optional.of(rol));

    mockMvc.perform(get("/usuario/roles/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.rol").value("Admin"));
}

    @Test
    public void testGetRolByIdNotFound() throws Exception {
        when(rolesServiceMock.getRolesById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/roles/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rol no encontrado"));
    }



    @Test
    public void testHandleEntityNotFound() throws Exception {
        mockMvc.perform(get("/usuario/100"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }



}

