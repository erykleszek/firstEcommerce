package org.example.firstecommerce.project.controller;



import jakarta.validation.Valid;
import org.example.firstecommerce.project.payload.CategoryDTO;
import org.example.firstecommerce.project.payload.CategoryResponse;
import org.example.firstecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message") String message) {
   // public ResponseEntity<String> echoMessage(@RequestParam(name = "message", defaultValue = "Hello World") String message) {
        return new ResponseEntity<>("Echo message: " + message, HttpStatus.OK);
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse>getAllCategories(){
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO =  categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> DeleteCategory(@PathVariable Long categoryId) {

            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }
    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO>  updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                  @PathVariable Long categoryId) {
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
