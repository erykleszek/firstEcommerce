package org.example.firstecommerce.project.service;

import org.example.firstecommerce.project.model.Product;
import org.example.firstecommerce.project.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
