package ch.cern.todo.config;

import ch.cern.todo.model.Category;
import ch.cern.todo.model.Task;
import ch.cern.todo.repository.CategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class H2Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(H2Bootstrap.class);

    public static final List<Category> mockCategories = List.of(
            new Category(1, "Test1", "Test category 1"),
            new Category(2, "Test2", "Test category 2"),
            new Category(3, "Test3", "Test category 3"),
            new Category(4, "Test4", "Test category 4")
    );

    public static final List<Task> mockTask = List.of(
            new Task(1, "Task1", "Test category 1", new Date(), mockCategories.get(0)),
            new Task(2, "Task2", "Test category 2", new Date(), mockCategories.get(1)),
            new Task(3, "Task3", "Test category 3", new Date(), mockCategories.get(2))
    );

    @Bean
    CommandLineRunner initDb(CategoryRepository categoryRepository, TaskRepository taskRepository) {
        return (args) -> {
            mockCategories.forEach(category ->
                    logger.info(String.format("Preloading category %s", categoryRepository.save(category)))
            );

            mockTask.forEach(task ->
                    logger.info(String.format("Preloading task %s", taskRepository.save(task)))
            );
        };

    }

}
