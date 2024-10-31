package com.jacqueline.task.management.service;

import com.jacqueline.task.management.model.Task;
import com.jacqueline.task.management.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> get() {
        return taskRepository.findAll();
    }

    public Task get(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        taskRepository.deleteById(id);
    }

    public @Valid Task save(@Valid Task task) {
        taskRepository.save(task);
        return task;
    }
}
