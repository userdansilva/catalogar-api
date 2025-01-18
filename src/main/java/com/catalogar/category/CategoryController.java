package com.catalogar.category;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, Validator validator) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(
            @PathParam("sort") String sort,
            @PathParam("field") String field
    ) {
        CategoryFilter filter = new CategoryFilter(sort, field);

        List<Category> categories = categoryService.getAll(filter);

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getById(
            @PathVariable("id") UUID id
    ) {
        Category category = categoryService.getById(id);

        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<Category> create(
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        Category category = categoryService.create(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> update(
            @PathVariable UUID id,
            @RequestBody CategoryRequest categoryRequest,
            BindingResult bindingResult
    ) {
        Category category = categoryService.update(id, categoryRequest);

        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable UUID id
    ) {
        categoryService.delete(id);
    }
}
