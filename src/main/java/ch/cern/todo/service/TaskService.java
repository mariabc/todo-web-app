package ch.cern.todo.service;

import ch.cern.todo.ResourceNotFoundException;
import ch.cern.todo.model.Category;
import ch.cern.todo.model.Task;
import ch.cern.todo.repository.CategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Task> findAll() {
        logger.info("> findAll");
        return (List<Task>) taskRepository.findAll();
    }

    public Optional<Task> findById(Long taskId) {
        logger.info("> findById {}", taskId);
        return taskRepository.findById(taskId);
    }

    @Transactional
    public Task create(Long categoryId, Task task) {
        logger.info("> save");
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            logger.info("> category {} not found. Task cant be created", categoryId);
            throw new ResourceNotFoundException();
        }

        task.setCategory(categoryOptional.get());

        return taskRepository.save(task);
    }


    @Transactional
    public Task update(Long taskId, Long categoryId, Task task) {
        logger.info("> update {}", taskId);

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            logger.info("> task {} not found", taskId);
            throw new ResourceNotFoundException();
        }

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            logger.info("> category {} not found", categoryId);
            throw new ResourceNotFoundException();
        }

        task.setCategory(categoryOptional.get());
        task.setTaskId(taskId);

        return taskRepository.save(task);
    }


    @Transactional
    public void delete(Long taskId) {
        logger.info("> delete {}", taskId);
        try {
            taskRepository.deleteById(taskId);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("> task not found {}", taskId);
        }
    }
}
