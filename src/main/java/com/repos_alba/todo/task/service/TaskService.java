package com.repos_alba.todo.task.service;

import com.repos_alba.todo.task.exception.EmptyTaskListException;
import com.repos_alba.todo.task.model.Task;
import com.repos_alba.todo.task.model.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    public List<Task> findAll(){
        List<Task> result = taskRepository.findAll();
        if(result.isEmpty()){
            throw new EmptyTaskListException();
        }
        return result;
    }
}
