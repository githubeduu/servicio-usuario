package com.example.serviciousuario.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "descripcion", columnDefinition = "CLOB")
    private String descripcion;

    @Nonnull
    @Column(name = "imagenurl")
    private String imagenurl;

    @Nonnull
    @Column(name = "precio")
    private int precio;

    @Nonnull
    @Column(name = "descuento")
    private boolean descuento;

    @Nonnull
    @Column(name = "categoriaid")
    private int categoriaid;

    //getters
    public Long getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getImagenurl(){
        return imagenurl;
    }    

    public int getPrecio(){
        return precio;
    }

    public boolean getDescuento(){
        return descuento;
    }

    public int getCategoriaid(){
        return categoriaid;
    }


    //setters
    public void setId(long id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setImagenurl(String imagenUrl){
        this.imagenurl = imagenurl;
    }

    public void setPrecio(int precio){
        this.precio = precio;
    }

    public void setDescuento(boolean descuento){
        this.descuento = descuento;
    }

    public void setcategoriaid(int categoriaid){
        this.categoriaid = categoriaid;
    }


}
