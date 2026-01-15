package com.repos_alba.todo.shared.init;

import com.repos_alba.todo.category.model.CategoryRepository;
import com.repos_alba.todo.task.service.TaskService;
import com.repos_alba.todo.user.dto.CreateUserRequest;
import com.repos_alba.todo.user.model.User;
import com.repos_alba.todo.user.model.UserRole;
import com.repos_alba.todo.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final UserService userService;

    @PostConstruct
    public void init() {
        List<User> users =insertUsers();
    }

    /*
        Solamente devuelve aquellos que son UserRole.USER
        para poder usarlos como autores de Task
     */
    private List<User> insertUsers() {

        List<User> result = new ArrayList<>();

        CreateUserRequest req = CreateUserRequest.builder()
                .username("user")
                .email("user@user.com")
                .password("1234")
                .verifyPassword("1234")
                .fullname("The user")
                .build();
        User user = userService.registerUser(req);
        result.add(user);

        CreateUserRequest req2 = CreateUserRequest.builder()
                .username("admin")
                .email("admin@openwebinars.net")
                .password("1234")
                .verifyPassword("1234")
                .fullname("Administrador")
                .build();
        User user2 = userService.registerUser(req2);

        userService.changeRole(user2, UserRole.ADMIN);

        return result;
    }
}
