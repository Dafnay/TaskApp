package com.repos_alba.todo.task.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateTaskRequest {

    @NotBlank(message = "Title is required")
    protected String title;

    protected String description;
    protected String tags;

    @NotNull(message = "Category is required")
    @Positive(message = "Please select a valid category")
    protected Long categoryId;
}
