package com.repos_alba.todo.task.service;

import com.repos_alba.todo.category.model.Category;
import com.repos_alba.todo.category.model.CategoryRepository;
import com.repos_alba.todo.tag.service.TagService;
import com.repos_alba.todo.task.dto.CreateTaskRequest;
import com.repos_alba.todo.task.dto.EditTaskRequest;
import com.repos_alba.todo.task.exception.EmptyTaskListException;
import com.repos_alba.todo.task.exception.TaskNotFoundException;
import com.repos_alba.todo.task.model.Task;
import com.repos_alba.todo.task.model.TaskRepository;
import com.repos_alba.todo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private  final TagService tagService;


    private List<Task> findAll(User user) {

        List<Task> result = null;

        if (user != null)
            result = taskRepository.findByAuthor(user, Sort.by("createdAt").ascending());
        else
            result = taskRepository.findAll(Sort.by("createdAt").ascending());

        if (result.isEmpty())
            throw new EmptyTaskListException();

        return result;
    }

    public List<Task> findAllByUser(User user, String filter) {
        List<Task> tasks = findAll(user);

        if (filter == null) {
            return tasks;
        }

        return switch (filter) {
            case "completed" -> tasks.stream().filter(Task::isCompleted).toList();
            case "pending" -> tasks.stream().filter(t -> !t.isCompleted()).toList();
            default -> tasks;
        };
    }

    public List<Task> findAllAdmin() {
        return findAll(null);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(CreateTaskRequest req, User author) {
        return createOrEditTask(req, author);
    }

    public Task editTask(EditTaskRequest req) {
        return createOrEditTask(req, null);
    }


    private Task createOrEditTask(CreateTaskRequest req, User author) {

        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .build();

        if (req.getCategoryId() == null || req.getCategoryId() == -1L)
            req.setCategoryId(1L);
        Category category = categoryRepository.getReferenceById(req.getCategoryId());
        if (category == null) // Categoría por defecto
            category = categoryRepository.getReferenceById(1L);

        task.setCategory(category);

        // Procesamos los tags
        List<String> textTags = Arrays.stream(req.getTags().split(","))
                .map(String::trim)
                .toList();
        // Añadimos a task
        task.getTags().addAll(tagService.saveOrGet(textTags));

        // Esto si queremos editar un Task
        if (req instanceof EditTaskRequest editReq) {
            Task oldTask = findById(editReq.getId());
            task.setId(oldTask.getId());
            task.setCreatedAt(oldTask.getCreatedAt());
            task.setAuthor(oldTask.getAuthor());
            task.setCompleted(editReq.isCompleted());
        } else {
            task.setAuthor(author);
        }

        // Inserta o actualiza, según corresponda
        return taskRepository.save(task);

    }


    public Task toggleCompleted(Long id) {
        Task task = findById(id);
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

}
