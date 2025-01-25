package com.catalogar.service;

import com.catalogar.dto.CategoryFilterDto;
import com.catalogar.dto.CategoryRequestDto;
import com.catalogar.exception.ResourceNotFoundException;
import com.catalogar.exception.UniqueFieldConflictException;
import com.catalogar.mapper.CategoryMapper;
import com.catalogar.model.Category;
import com.catalogar.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<Category> getAll(CategoryFilterDto filterDto) {
        Sort sort = categoryMapper.toSort(filterDto);

        return categoryRepository.findAll(sort);
    }

    public Category getById(UUID id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria com id: " + id + " não encontrada"));
    }

    public Category create(CategoryRequestDto categoryRequestDto) {
        boolean existsByName = categoryRepository
                .existsByName(categoryRequestDto.name());

        if (existsByName) {
            throw new UniqueFieldConflictException(
                    "Categoria com o nome " + categoryRequestDto.name() + " já está cadastrada"
            );
        }

        Category category = categoryMapper.toCategory(categoryRequestDto);

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
