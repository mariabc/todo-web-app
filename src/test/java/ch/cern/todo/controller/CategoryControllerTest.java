package ch.cern.todo.controller;

import ch.cern.todo.config.H2Bootstrap;
import ch.cern.todo.model.Category;
import ch.cern.todo.service.CategoryService;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findCategories() throws Exception {

        mvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(H2Bootstrap.mockCategories.size()))
                );
    }

    @Test
    public void findCategoryById() throws Exception {

        mvc.perform(get("/api/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.name").value(H2Bootstrap.mockCategories.get(0).getName()))
                .andExpect(jsonPath("$.description").value(H2Bootstrap.mockCategories.get(0).getDescription()));
    }

    @Test
    public void newCategory() throws Exception {

        Category mockCategory = new Category(5, "Test5", "This is a test5");
        mvc.perform(post("/api/category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockCategory.getName()))
                .andExpect(jsonPath("$.description").value(mockCategory.getDescription())
                );

        Optional<Category> category = categoryService.findById(5L);
        assertThat(category.isPresent()).isTrue();
        assertThat(category.get().getName()).isEqualTo("Test5");
        assertThat(category.get().getDescription()).isEqualTo("This is a test5");

    }

    @Test
    public void updateCategory() throws Exception {

        Category mockCategory = categoryService.findById(2L).get();
        mockCategory.setName("Test2Updated");
        mockCategory.setDescription("This is test2 updated");

        mvc.perform(put("/api/category/2")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockCategory)))
                .andExpect(status().isOk());

        Optional<Category> category = categoryService.findById(2L);
        assertThat(category.isPresent()).isTrue();
        assertThat(category.get().getName()).isEqualTo(mockCategory.getName());
        assertThat(category.get().getDescription()).isEqualTo(mockCategory.getDescription());

    }

    @Test
    public void updateCategory_notFound() throws Exception {

        Category mockCategory = categoryService.findById(2L).get();
        mockCategory.setName("Test7Updated");
        mockCategory.setDescription("This is test7 updated");

        mvc.perform(put("/api/category/7")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockCategory)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeCategory() throws Exception {

        mvc.perform(delete("/api/category/4"))
                .andExpect(status().isOk()
                );

        Optional<Category> category = categoryService.findById(4L);
        assertThat(category.isPresent()).isFalse();
    }
}
