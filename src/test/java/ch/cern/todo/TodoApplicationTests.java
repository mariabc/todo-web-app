package ch.cern.todo;

import ch.cern.todo.controller.CategoryController;
import ch.cern.todo.controller.TaskController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TodoApplicationTests {

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private TaskController taskController;

    @Test
    void contextLoads() throws Exception {
        assertThat(categoryController).isNotNull();
        assertThat(taskController).isNotNull();
    }


}
