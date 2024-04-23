package com.example.serviciousuario.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "roles")
public class Roles{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Nonnull
    @Column(name = "rol")
    private String rol;



    public Long getId(){
        return id;
    }

    public String getRol(){
        return rol;
    }


    //setters
    public void setId(Long id){
        this.id = id;
    }

    public void setRol(String rol){
        this.rol = rol;
    }
}