package com.example.serviciousuario.service;
import java.util.List;
import java.util.Optional;
import com.example.serviciousuario.model.Productos;

public interface ProductoService {
    List<Productos> getAllProductos();
    Optional<Productos> getProductosById(Long id); 
    List<Productos> getProductosByCategoriaId(Long categoriaId);
    Productos crearProducto(Productos producto);
    Productos actualizarProducto(Productos producto);
    void eliminarProducto(Long id);
}

