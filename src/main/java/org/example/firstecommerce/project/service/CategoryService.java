package org.example.firstecommerce.project.service;

import org.example.firstecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);

}
