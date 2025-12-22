package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.CategoryDTO;
import com.example.aiproductcatalog.web.api.mapper.CategoryMapper;
import com.example.aiproductcatalog.web.model.Category;
import com.example.aiproductcatalog.web.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        Category saved = repository.save(category);
        return mapper.toDto(saved);
    }

    public void deleteCategory(Integer id) {
        repository.deleteById(id);
    }
}
