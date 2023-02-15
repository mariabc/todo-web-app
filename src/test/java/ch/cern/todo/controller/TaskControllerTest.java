package ch.cern.todo.controller;

import ch.cern.todo.config.H2Bootstrap;
import ch.cern.todo.model.Task;
import ch.cern.todo.service.CategoryService;
import ch.cern.todo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findTasks() throws Exception {

        mvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(H2Bootstrap.mockTask.size()))
                );
    }

    @Test
    public void findTaskById() throws Exception {

        mvc.perform(get("/api/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(1))
                .andExpect(jsonPath("$.name").value(H2Bootstrap.mockTask.get(0).getName()))
                .andExpect(jsonPath("$.description").value(H2Bootstrap.mockTask.get(0).getDescription()))
                .andExpect(jsonPath("$.category.categoryId").value(H2Bootstrap.mockTask.get(0).getCategory().getCategoryId()));
    }

    @Test
    public void newTask() throws Exception {

        Task mockTask = new Task(4, "Test4", "This is a test4", new Date(),
                categoryService.findById(H2Bootstrap.mockTask.get(0).getCategory().getCategoryId()).get());

        mvc.perform(post("/api/task/category/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockTask.getName()))
                .andExpect(jsonPath("$.description").value(mockTask.getDescription()))
                .andExpect(jsonPath("$.category.categoryId").value(H2Bootstrap.mockTask.get(0).getCategory().getCategoryId())
                );

        Optional<Task> task = taskService.findById(4L);
        assertThat(task.isPresent()).isTrue();
        assertThat(task.get().getName()).isEqualTo("Test4");
        assertThat(task.get().getDescription()).isEqualTo("This is a test4");

    }

    @Test
    public void updateTask() throws Exception {

        Task mockTask = taskService.findById(2L).get();
        mockTask.setName("Test2Updated");
        mockTask.setDescription("This is test2 updated");

        mvc.perform(put("/api/task/2/category/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockTask)))
                .andExpect(status().isOk());

        Optional<Task> task = taskService.findById(2L);
        assertThat(task.isPresent()).isTrue();
        assertThat(task.get().getName()).isEqualTo(mockTask.getName());
        assertThat(task.get().getDescription()).isEqualTo(mockTask.getDescription());
        assertThat(task.get().getCategory().getCategoryId()).isEqualTo(1L);

    }

    @Test
    public void removeTask() throws Exception {

        mvc.perform(delete("/api/task/3"))
                .andExpect(status().isOk()
                );

        Optional<Task> task = taskService.findById(3L);
        assertThat(task.isPresent()).isFalse();
    }
}
