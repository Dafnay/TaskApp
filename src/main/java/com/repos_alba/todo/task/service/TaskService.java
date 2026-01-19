package com.repos_alba.todo.task.service;

import com.repos_alba.todo.category.model.Category;
import com.repos_alba.todo.category.model.CategoryRepository;
import com.repos_alba.todo.tag.service.TagService;
import com.repos_alba.todo.task.dto.CreateTaskRequest;
import com.repos_alba.todo.task.exception.EmptyTaskListException;
import com.repos_alba.todo.task.model.Task;
import com.repos_alba.todo.task.model.TaskRepository;
import com.repos_alba.todo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private  final TagService tagService;


    public List<Task> findAll(){
        List<Task> result = taskRepository.findAll();
        if(result.isEmpty()){
            throw new EmptyTaskListException();
        }
        return result;
    }

    public Task createTask(CreateTaskRequest req, User author) {
        return createOrEditTask(req, author);
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

        // Procesamos los tags que vienen en forma de tag1,tag2,tag3
        List<String> textTags = Arrays.stream(req.getTags().split(","))
                .map(String::trim)
                .toList();
        // Los añadimos a task
        task.getTags().addAll(tagService.saveOrGet(textTags));


            task.setAuthor(author);


        // Inserta o actualiza, según corresponda
        return taskRepository.save(task);

    }
}
