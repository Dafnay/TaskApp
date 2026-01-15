package com.repos_alba.todo.task.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id){
        super("Task with Id: %d not found" .formatted(id));
    }
}
