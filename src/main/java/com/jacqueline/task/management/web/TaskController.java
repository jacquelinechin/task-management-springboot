package com.jacqueline.task.management.web;

import com.jacqueline.task.management.model.Task;
import com.jacqueline.task.management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Iterable<Task> get() {
        return taskService.get();
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable Integer id) {
        Task task = taskService.get(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return task;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Task task = taskService.get(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        taskService.remove(id);
    }

    @PostMapping
    public Task create(@RequestBody @Valid Task task) {
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Integer id, @RequestBody @Valid Task updatedTask) {
        Task existingTask = taskService.get(id);
        if (existingTask == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        return taskService.save(existingTask);
    }
}
