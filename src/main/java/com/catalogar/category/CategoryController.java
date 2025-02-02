package com.catalogar.category;

import com.catalogar.common.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAll(
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
            String field,

            @RequestParam(
                    name = "page",
                    required = false,
                    defaultValue = "1"
            )
            String page,

            @RequestParam(
                    name = "perPage",
                    required = false,
                    defaultValue = "10"
            )
            String perPage
    ) {
        CategoryFilterDto filterDto = new CategoryFilterDto(sort, field, page, perPage);

        Set<ConstraintViolation<CategoryFilterDto>> violations = validator.validate(filterDto);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Page<Category> categories = categoryService
                .getAll(filterDto);

        return ResponseEntity.ok()
                .body(categoryMapper.toApiResponse(categories));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getById(
            @PathVariable("id") UUID id
    ) {
        Category category = categoryService.getById(id);

        return ResponseEntity.ok()
                .body(categoryMapper.toApiResponse(category));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> create(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        Category category = categoryService.create(categoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper
                        .toApiResponse(
                                category,
                                "Categoria "
                                        + category.getName()
                                        + " criada com sucesso!"
                        ));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryRequestDto categoryRequest
    ) {
        Category category = categoryService.update(id, categoryRequest);

        return ResponseEntity.ok()
                .body(categoryMapper
                        .toApiResponse(
                                category,
                                "Categoria "
                                + category.getName()
                                + " atualizada com sucesso!"
                        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID id
    ) {
        Category category = categoryService.getById(id);

        ApiResponse<Void> apiResponse = new ApiResponse<Void>(
                "Categoria "
                + category.getName()
                + " removida com sucesso!"
        );

        categoryService.delete(id);

        return ResponseEntity.ok().body(apiResponse);
    }
}
