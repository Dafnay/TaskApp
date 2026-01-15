package com.repos_alba.todo.user.controller;

import com.repos_alba.todo.user.dto.CreateUserRequest;
import com.repos_alba.todo.user.model.UserRepository;
import com.repos_alba.todo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/auth/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "register";
    }

    @PostMapping("/auth/register/submit")
    public String processRegisterForm(
            @Valid @ModelAttribute("user") CreateUserRequest request,
            BindingResult bindingResult) {

        // Validar username duplicado (solo si no está vacío)
        if (request.getUsername() != null && !request.getUsername().isBlank()
                && userRepository.existsByUsername(request.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "El nombre de usuario ya está en uso");
        }

        // Validar email duplicado (solo si no está vacío)
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && userRepository.existsByEmail(request.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "El email ya está registrado");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(request);
        return "redirect:/login";
    }

}