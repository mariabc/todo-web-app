package ch.cern.todo.controller;

import ch.cern.todo.model.Category;
import ch.cern.todo.service.CategoryService;
import com.google.common.base.Preconditions;
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

    /**
     * Web service endpoint to find all categories
     *
     * @return the list of found categories
     */
    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    /**
     * Web service endpoint to find a category by id
     *
     * It receives the categoryId as parameter
     *
     * @param categoryId
     * @return a category if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable(value = "id") Long categoryId) {
        Optional<Category> category = categoryService.findById(categoryId);
        return category.isPresent() ? ResponseEntity.ok().body(category.get()) : ResponseEntity.notFound().build();
    }

    /**
     *
     *  Web service endpoint to create a category.
     *
     *  It receives the category as parameter
     *
     * @param category
     * @return the created category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> createCategory(@Validated @RequestBody Category category) {
        Preconditions.checkNotNull(category);
        return ResponseEntity.ok().body(categoryService.create(category));
    }


    /**
     * Web service endpoint to update a category.
     *
     * It receives the categoryId and the category as parameter
     *
     *
     * @param categoryId
     * @param category
     * @return the updated category
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId, @Validated @RequestBody Category category) {
        Preconditions.checkNotNull(category);
        Category updatedCategory = categoryService.update(categoryId, category);
        return ResponseEntity.ok().body(updatedCategory);

    }

    /**
     * Web service endpoint to delete a category.
     *
     * @param categoryId
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable(value = "id") Long categoryId) {
        categoryService.delete(categoryId);
    }
}
