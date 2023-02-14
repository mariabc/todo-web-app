package ch.cern.todo.service;

import ch.cern.todo.model.Category;
import ch.cern.todo.model.Task;
import ch.cern.todo.repository.CategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<Task> tasks = (List<Task>) taskRepository.findAll();

        logger.info("< findAll");
        return tasks;
    }

    public Optional<Task> findById(Long taskId) {
        logger.info("> findById {}", taskId);

        Optional<Task> task = taskRepository.findById(taskId);

        logger.info("< findById {}", taskId);

        return task;
    }

    @Transactional
    public Task create(Long categoryId, Task task) {
        logger.info("> save");

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            return null;
        }

        task.setCategory(categoryOptional.get());

        Task newTask = taskRepository.save(task);

        logger.info("< save");

        return newTask;
    }


    @Transactional
    public Task update(Long taskId, Long categoryId, Task task) {
        logger.info("> update");

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            return null;
        }

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            return null;
        }

        task.setCategory(categoryOptional.get());
        task.setTaskId(taskId);

        return taskRepository.save(task);
    }


    @Transactional
    public void delete(Long taskId) {
        logger.info("> delete {}", taskId);

        taskRepository.deleteById(taskId);

        logger.info("< delete {}", taskId);
    }
}
