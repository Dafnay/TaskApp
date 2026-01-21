package com.repos_alba.todo.shared.init;

import com.repos_alba.todo.category.model.Category;
import com.repos_alba.todo.category.model.CategoryRepository;
import com.repos_alba.todo.task.dto.CreateTaskRequest;
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
    private final CategoryRepository categoryRepository;
    private final TaskService taskService;
    private final com.repos_alba.todo.user.model.UserRepository userRepository;

    @PostConstruct
    public void init() {
        // Solo insertar datos si no existen usuarios
        if (userRepository.count() == 0) {
            insertCategories();
            List<User> users = insertUsers();
            insertTasks(users.get(0));
        }
    }

    private List<User> insertUsers() {

        List<User> result = new ArrayList<>();

        CreateUserRequest req = CreateUserRequest.builder()
                .username("user")
                .email("user@user.com")
                .password("123456")
                .verifyPassword("123456")
                .fullname("The user")
                .build();
        User user = userService.registerUser(req);
        result.add(user);

        CreateUserRequest req2 = CreateUserRequest.builder()
                .username("admin")
                .email("admin@admin.com")
                .password("123456")
                .verifyPassword("123456")
                .fullname("Administrador")
                .build();
        User user2 = userService.registerUser(req2);

        userService.changeRole(user2, UserRole.ADMIN);

        return result;
    }

    private void insertCategories() {
        categoryRepository.save(Category.builder().title("Main").build());
    }

    private void insertTasks(User author) {

        CreateTaskRequest req1 = CreateTaskRequest.builder()
                .title("First task!")
                .description("Lorem ipsum dolor sit amet")
                .tags("tag1,tag2,tag3")
                .build();

        taskService.createTask(req1, author);

        CreateTaskRequest req2 = CreateTaskRequest.builder()
                .title("Second task!")
                .description("Lorem ipsum dolor sit amet")
                .tags("tag1,tag2,tag4")
                .build();

        taskService.createTask(req2, author);

    }
}
