package com.catalogar.service;

import com.catalogar.dto.CategoryFilterDto;
import com.catalogar.dto.CategoryRequestDto;
import com.catalogar.exception.ResourceNotFoundException;
import com.catalogar.exception.UniqueFieldConflictException;
import com.catalogar.model.Category;
import com.catalogar.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll(CategoryFilterDto filter) {
        Sort.Direction direction = Sort.Direction.valueOf(
                Sort.Direction.class,
                filter.order().toUpperCase());

        return categoryRepository.findAll(Sort.by(
                direction,
                filter.field()));
    }

    public Category getById(UUID id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria com id: " + id + " não encontrada"));
    }

    public Category create(CategoryRequestDto categoryRequest) {
        boolean existsByName = categoryRepository
                .existsByName(categoryRequest.name());

        if (existsByName) {
            throw new UniqueFieldConflictException(
                    "Categoria com o nome " + categoryRequest.name() + " já está cadastrada"
            );
        }

        Category category = new Category(
                categoryRequest.name(),
                categoryRequest.slug(),
                categoryRequest.textColor(),
                categoryRequest.backgroundColor()
        );

        if (!categoryRequest.isActive()) {
            category.setDisabledAt(LocalDateTime.now());
        }

        return categoryRepository.save(category);
    }

    public Category update(UUID id, CategoryRequestDto categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria com id: " + id + " não encontrada"
                ));

        category.setName(categoryRequest.name());
        category.setTextColor(categoryRequest.textColor());
        category.setBackgroundColor(categoryRequest.backgroundColor());

        if (!categoryRequest.isActive()) {
            category.setDisabledAt(LocalDateTime.now());
        }

        return categoryRepository.save(category);
    }

    public void delete(UUID id) {
        boolean existsById = categoryRepository.existsById(id);

        if (!existsById) {
            throw new ResourceNotFoundException(
                    "Categoria com id: " + id + " não encontrada"
            );
        }

        categoryRepository.deleteById(id);
    }
}
