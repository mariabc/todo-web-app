package ch.cern.todo.controller;


import ch.cern.todo.model.Task;
import ch.cern.todo.service.TaskService;
import com.google.common.base.Preconditions;
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

    /**
     * Web service to retrieve the list of tasks
     *
     * @return the list of tasks
     */
    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    /**
     * Web service to retrieve a particular task
     *
     * It receives the taskId as parameter
     *
     * @param taskId
     * @return the task
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId) {
        Optional<Task> task = taskService.findById(taskId);
        return task.isPresent() ? ResponseEntity.ok().body(task.get()) : ResponseEntity.notFound().build();
    }

    /**
     * Web service to create a new task given a categoryId
     *
     * It receives the categoryId as parameter as well as the task fields
     *
     * @param categoryId
     * @param task
     * @return the newly created task
     */
    @PostMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Task> createTask(@PathVariable(value = "categoryId") Long categoryId, @Validated @RequestBody Task task) {
        Preconditions.checkNotNull(task);
        return ResponseEntity.ok().body(taskService.create(categoryId, task));
    }

    /**
     * Web service to update a given task
     *
     * It recieve as parameters the task, the taskId and the categoryId
     *
     * @param taskId
     * @param categoryId
     * @param task
     * @return the updated task
     */
    @PutMapping("/{id}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId, @PathVariable(value = "categoryId") Long categoryId, @Validated @RequestBody Task task) {
        Preconditions.checkNotNull(task);
        Task updatedTask = taskService.update(taskId, categoryId, task);
        return ResponseEntity.ok().body(updatedTask);
    }

    /**
     * Web service to delete a task
     *
     * Receives as parameter the taskId
     *
     * @param taskId
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable(value = "id") Long taskId) {
        taskService.delete(taskId);
    }
}
