package com.example.serviciousuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.serviciousuario.model.Productos;
import com.example.serviciousuario.repository.ProductosRepository;

@Service
public class ProductoServicelmpl implements ProductoService {
    @Autowired
    private ProductosRepository productosRepository;

    @Override
    public List<Productos> getAllProductos(){
        return productosRepository.findAll();
    }

    @Override
    public Optional<Productos> getProductosById(Long id){
        return productosRepository.findById(id);
    }
}
