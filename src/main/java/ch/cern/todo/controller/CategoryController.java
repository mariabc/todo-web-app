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
    public ResponseEntity<Category> findById(@PathVariable(value = "id") Long categoryId) {

        Optional<Category> category = categoryService.findById(categoryId);

        return category.isPresent() ? ResponseEntity.ok().body(category.get()) : ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Validated @RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.create(category));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId, @Validated @RequestBody Category category) {

        Category updatedCategory = categoryService.update(categoryId,category);

        return updatedCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(value = "id") Long categoryId) {
        categoryService.delete(categoryId);
    }
}
