package com.example.serviciousuario.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.serviciousuario.model.Roles;
import com.example.serviciousuario.model.Usuario;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Test
    public void crearUsuarioTest(){
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
}
