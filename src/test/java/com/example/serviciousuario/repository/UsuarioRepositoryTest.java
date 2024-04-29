package com.example.serviciousuario.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.serviciousuario.model.Roles;
import com.example.serviciousuario.model.Usuario;

import net.minidev.json.parser.ParseException;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Test
    public void CrearUsuarioTest(){
        Roles rol = rolesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Usuario usuario = new Usuario();
        usuario.setNombre("Andrea Miranda");
        usuario.setRut("12345678-9");
        usuario.setDireccion("Rio boroa 1744");
        usuario.setComuna("Cerro navia");
        usuario.setRol(rol);

        Usuario resultado = usuarioRepository.save(usuario);

        assertNotNull(resultado.getId());
        assertEquals("Andrea Miranda", resultado.getNombre());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("Rio boroa 1744", resultado.getDireccion());
        assertEquals("Cerro navia", resultado.getComuna());
        assertNotNull(resultado.getRoles());
    }

    @Test
    public void BuscarUsuarioPorIdTest() throws ParseException {
        Roles rol = rolesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Usuario usuario = new Usuario();
        usuario.setNombre("Andrea Miranda");
        usuario.setRut("12345678-9");
        usuario.setDireccion("Rio boroa 1744");
        usuario.setComuna("Cerro navia");
        usuario.setRol(rol);
 
        usuario = usuarioRepository.save(usuario);    
        Optional<Usuario> resultado = usuarioRepository.findById(usuario.getId());

        assertNotNull(resultado.get().getId());
        assertEquals("Andrea Miranda", resultado.get().getNombre());
        assertEquals("12345678-9", resultado.get().getRut());
        assertEquals("Rio boroa 1744", resultado.get().getDireccion());
        assertEquals("Cerro navia", resultado.get().getComuna());
        assertNotNull(resultado.get().getRoles());
    }
}
