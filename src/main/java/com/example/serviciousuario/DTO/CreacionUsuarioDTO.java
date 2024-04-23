package com.example.serviciousuario.DTO;

import java.io.Serializable;

import com.example.serviciousuario.model.Roles;

public class CreacionUsuarioDTO implements Serializable {
    private Long id;
    private String nombre;
    private String rut;
    private String direccion;
    private String comuna;
    private Long rolId;
    private String username;
    private String password;
    private Roles rol;

    //getters    
    public Long getId() {
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getRut(){
        return rut;
    }

    public String getDireccion(){
        return direccion;
    }

    public String getComuna(){
        return comuna;
    }

    public Long getRolId(){
        return rolId;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public Roles getRol(){
        return rol;
    }
   
    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setRut(String rut){
        this.rut = rut;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public void setComuna(String comuna){
        this.comuna = comuna;
    }

    public void setRolId(Long rolId){
        this.rolId = rolId;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRol(Roles rol){
        this.rol = rol;
    }
}