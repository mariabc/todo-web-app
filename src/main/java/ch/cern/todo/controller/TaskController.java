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

        if(task.isPresent()) {
            return ResponseEntity.ok().body(task.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Task createTask(@Validated @RequestBody Task task) {
        return taskService.create(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable(value = "id") Long taskId) {
        taskService.delete(taskId);
    }
}
