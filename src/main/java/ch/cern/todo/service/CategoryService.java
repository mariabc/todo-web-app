package ch.cern.todo.service;

import ch.cern.todo.ResourceNotFoundException;
import ch.cern.todo.model.Category;
import ch.cern.todo.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return (List<Category>) categoryRepository.findAll();
    }

    public Optional<Category> findById(Long categoryId) {
        logger.info("> findById {}", categoryId);
        return categoryRepository.findById(categoryId);
    }

    @Transactional
    public Category create(Category category) {
        logger.info("> save");
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long categoryId, Category category) {
        logger.info("> update {}", categoryId);

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            logger.info("> category {} not found", categoryId);
            throw new ResourceNotFoundException();
        }

        category.setCategoryId(categoryId);

        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long categoryId) {
        logger.info("> delete {}", categoryId);
        try {
            categoryRepository.deleteById(categoryId);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("> category not found {}", categoryId);
        }
    }

}
