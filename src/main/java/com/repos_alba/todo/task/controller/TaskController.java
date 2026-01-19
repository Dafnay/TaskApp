package com.repos_alba.todo.task.controller;


import com.repos_alba.todo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping({"/", "list", "/task"})
    public String taskList(Model model){
        model.addAttribute("taskList", taskService.findAll());
        return "task-list";
    }

    @GetMapping( value= {"/", "list", "/task"}, params = "emptyListError")
    public String createTask(Model model){
        return "task-list";
    }
}
