package com.example.serviciousuario.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Map;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private AuthService authService;
    

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
    
        // Validar las credenciales
        Auth auth = authService.validatePassword(username, password);
    
        if (auth != null) {
            Long usuarioId = auth.getUsuario().getId(); // Obtener el ID del usuario desde Auth
            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(usuarioId);
    
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                return ResponseEntity.status(200).body(usuario); // Devolver el usuario encontrado
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contraseña incorrecta");
        }
    }
    
    
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.getAllUsuario().stream()
            .map(usuario -> EntityModel.of(usuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(usuario.getId())).withSelfRel()))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarios());
        return CollectionModel.of(usuarios, linkTo.withRel("usuarios"));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRolById(@PathVariable Long id) {
        Optional<Roles> rol = rolesService.getRolesById(id);
        if (rol.isPresent()) {
            EntityModel<Roles> resource = EntityModel.of(rol.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getRolById(id)).withSelfRel());
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
    }


    @GetMapping("/roles")
    public ResponseEntity<CollectionModel<EntityModel<Roles>>> getRoles() {
        List<EntityModel<Roles>> roles = rolesService.getAllRoles().stream()
            .map(rol -> EntityModel.of(rol,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getRolById(rol.getId())).withSelfRel()))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkToAllRoles = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getRoles());
        CollectionModel<EntityModel<Roles>> resources = CollectionModel.of(roles, linkToAllRoles.withRel("all-roles"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        if (usuario.isPresent()) {
            EntityModel<Usuario> resource = EntityModel.of(usuario.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarios()).withRel("all-usuarios"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }  
  
    @PostMapping
    public ResponseEntity<?> createUsuario(@Validated @RequestBody CreacionUsuarioDTO usuarioDto) {
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(usuarioDto.getNombre());
            nuevoUsuario.setRut(usuarioDto.getRut());
            nuevoUsuario.setDireccion(usuarioDto.getDireccion());
            nuevoUsuario.setComuna(usuarioDto.getComuna());
            nuevoUsuario.setRolId(usuarioDto.getRolId());            
           
            Roles rol = rolesService.getRolesById(usuarioDto.getRolId()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            nuevoUsuario.setRol(rol);

            nuevoUsuario = usuarioService.createUsuario(nuevoUsuario);

            Auth auth = new Auth();
            auth.setUsername(usuarioDto.getUsername());
            auth.setPassword(usuarioDto.getPassword());
            auth.setUsuario(nuevoUsuario); // Asociar el usuario creado con este Auth
    
            // Crear el Auth en la base de datos
            authService.createAuth(auth);
          
            EntityModel<Usuario> resource = EntityModel.of(nuevoUsuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(nuevoUsuario.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarios()).withRel("all-usuarios"));
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el usuario: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
            EntityModel<Usuario> resource = EntityModel.of(updatedUsuario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarioById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarios()).withRel("all-usuarios"));
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {

            System.out.println("token" +    token   );
            // Valida el token en el servicio de autenticación
            UserResponse userResponse = authService.validateToken(token);
            if (userResponse == null) {
                
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
            }
            usuarioService.deleteUsuario(id);
            Map<String, String> response = Collections.singletonMap("message", "Usuario eliminado correctamente");

            EntityModel<Map<String, String>> resource = EntityModel.of(response,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsuarios()).withRel("all-usuarios"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).createUsuario(null)).withRel("create-usuario"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}