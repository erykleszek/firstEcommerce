package org.example.firstecommerce.project.service;

import org.example.firstecommerce.project.model.Product;
import org.example.firstecommerce.project.payload.ProductDTO;
import org.example.firstecommerce.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, Product product);
}
