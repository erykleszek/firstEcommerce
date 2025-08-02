package org.example.firstecommerce.project.service;

import org.example.firstecommerce.project.model.Category;
import org.example.firstecommerce.project.payload.CategoryDTO;
import org.example.firstecommerce.project.payload.CategoryResponse;



public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
