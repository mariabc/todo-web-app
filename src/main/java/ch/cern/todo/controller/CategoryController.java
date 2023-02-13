package ch.cern.todo.controller;

import ch.cern.todo.model.Category;
import ch.cern.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId) {

        Optional<Category> category = categoryService.findById(categoryId);

        if(category.isPresent()) {
            return ResponseEntity.ok().body(category.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Validated @RequestBody Category category) {
        Category newCategory =  categoryService.create(category);
        return ResponseEntity.ok().body(newCategory);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId, @Validated @RequestBody Category category) {

        Category updatedCategory = categoryService.update(categoryId,category);

        if (updatedCategory == null) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(value = "id") Long categoryId) {
        categoryService.delete(categoryId);
    }
}
