package com.repos_alba.todo.category.service;

import com.repos_alba.todo.category.model.Category;
import com.repos_alba.todo.category.model.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by("title").ascending());
    }
}
