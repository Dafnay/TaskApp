package com.repos_alba.todo.task.controller;


import com.repos_alba.todo.category.model.Category;
import com.repos_alba.todo.category.service.CategoryService;
import com.repos_alba.todo.task.dto.CreateTaskRequest;
import com.repos_alba.todo.task.service.TaskService;
import com.repos_alba.todo.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }
    @GetMapping({"/", "list", "/task"})
    public String taskList(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("taskList", taskService.findAllByUser(user));
        model.addAttribute("newTask", new CreateTaskRequest());
        return "task-list";
    }

    @GetMapping( value= {"/", "list", "/task"}, params = "emptyListError")
    public String createTask(Model model){
        model.addAttribute("newTask", new CreateTaskRequest());
        return "task-list";
    }

    @PostMapping("/task/submit")
    public String taskSubmit(
            @Valid @ModelAttribute("newTask") CreateTaskRequest req,
            BindingResult bindingResult,
            @AuthenticationPrincipal User author,
            Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("taskList", taskService.findAllByUser(author));
            return "task-list";
        }

        taskService.createTask(req, author);

        return "redirect:/";
    }
}
