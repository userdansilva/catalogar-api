package com.catalogar.controller;

import com.catalogar.dto.CategoryDto;
import com.catalogar.dto.CategoryFilterDto;
import com.catalogar.dto.CategoryRequestDto;
import com.catalogar.mapper.CategoryMapper;
import com.catalogar.model.Category;
import com.catalogar.service.CategoryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final Validator validator;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, Validator validator, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.validator = validator;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll(
            @RequestParam(
                    name = "sort",
                    required = false,
                    defaultValue = "desc"
            )
            String sort,
            @RequestParam(
                    name = "field",
                    required = false,
                    defaultValue = "createdAt"
            )
            String field
    ) {
        CategoryFilterDto filterDto = new CategoryFilterDto(sort, field);

        Set<ConstraintViolation<CategoryFilterDto>> violations = validator.validate(filterDto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        List<CategoryDto> categories = categoryService
                .getAll(filterDto)
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getById(
            @PathVariable("id") UUID id
    ) {
        Category category = categoryService.getById(id);

        return ResponseEntity.ok().body(categoryMapper.toDto(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        Category category = categoryService.create(categoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.toDto(category));
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequestDto categoryRequest
    ) {
        Category category = categoryService.update(id, categoryRequest);

        return ResponseEntity.ok().body(categoryMapper.toDto(category));
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable UUID id
    ) {
        categoryService.delete(id);
    }
}
