package com.example.serviciousuario.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
@Table(name = "usuario")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "nombre")
    private String nombre;

    @Nonnull
    @Column(name = "rut")
    private String rut;

    @Nonnull
    @Column(name = "direccion")
    private String direccion;

    @Nonnull
    @Column(name = "comuna")
    private String comuna;

    @Column(name = "rol_id", insertable = false, updatable = false)
    private Long rolId;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Roles roles;

    //getters
    public Long getId(){
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

    public Roles getRoles(){
        return roles;
    }

    public Long getRolId(){
        return rolId;
    }   


    //setters
    public void setId(Long id){
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

    public void setRol(Roles roles){
        this.roles = roles;
    }
    
    public void setRolId(Long rolId){
        this.rolId = rolId;
    }
  
}

