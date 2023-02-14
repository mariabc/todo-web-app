package ch.cern.todo.controller;


import ch.cern.todo.model.Task;
import ch.cern.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId) {

        Optional<Task> task = taskService.findById(taskId);

        return task.isPresent() ? ResponseEntity.ok().body(task.get()) : ResponseEntity.notFound().build();

    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Task> createTask(@PathVariable(value = "categoryId") Long categoryId, @Validated @RequestBody Task task) {
        Task newTask = taskService.create(categoryId, task);

        return newTask == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok().body(newTask);
    }

    @PutMapping("/{id}/category/{categoryId}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId, @PathVariable(value = "categoryId") Long categoryId, @Validated @RequestBody Task task) {

        Task updatedTask = taskService.update(taskId, categoryId, task);

        return updatedTask == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable(value = "id") Long taskId) {
        taskService.delete(taskId);
    }
}
