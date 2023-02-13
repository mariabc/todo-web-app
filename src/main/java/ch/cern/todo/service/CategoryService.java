package ch.cern.todo.service;

import ch.cern.todo.model.Category;
import ch.cern.todo.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        logger.info("> findAll");

        List<Category> category = (List<Category>) categoryRepository.findAll();

        logger.info("< findAll");
        return category;
    }

    public Optional<Category> findById(Long categoryId) {
        logger.info("> findById {}", categoryId);

        Optional<Category> category = categoryRepository.findById(categoryId);

        logger.info("< findById {}", categoryId);
        return category;
    }
    @Transactional
    public Category create(Category category) {
        logger.info("> save");

        Category newCategory = categoryRepository.save(category);

        logger.info("< save");

        return newCategory;
    }

    @Transactional
    public Category update(Long categoryId, Category category) {
        logger.info("> update");

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            return null;
        }

        category.setCategoryId(categoryId);

        Category updatedCategory = categoryRepository.save(category);

        logger.info("< update");

        return updatedCategory;
    }

    @Transactional
    public void delete(Long categoryId) {
        logger.info("> delete {}", categoryId);

        categoryRepository.deleteById(categoryId);

        logger.info("< delete {}", categoryId);
    }



}
