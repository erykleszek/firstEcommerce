package org.example.firstecommerce.project.controller;



import org.example.firstecommerce.project.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public/categories") // Podstawowa ścieżka
@CrossOrigin(origins = "*")

public class CategoryController {

    private List<Category> categories = new ArrayList<>();

    // Obsłuży HTTP GET na /api/public/categories
    @GetMapping
    public List<Category> getAllCategories(){
        return categories;
    }

    // Obsłuży HTTP POST na /api/public/categories
    @PostMapping
    public String createCategory(@RequestBody Category category){
        categories.add(category);
        return "Category added successfully: " + category.getCategoryName();
    }

}