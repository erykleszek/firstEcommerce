package org.example.firstecommerce.project.repositories;

import jakarta.validation.constraints.NotBlank;
import org.example.firstecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank String categoryName);
}
