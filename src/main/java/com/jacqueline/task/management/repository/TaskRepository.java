package com.jacqueline.task.management.repository;

import com.jacqueline.task.management.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}