package com.catalogar.category;

import com.catalogar.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();

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
            @Valid @RequestBody NewCategoryRequest category,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();

            throw new BadRequestException(
                    "Requisição inválida",
                    bindingResult.getAllErrors()
            );
        }

        Category newCategory = categoryService.create(category);

        return ResponseEntity.ok().body(newCategory);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> update(
            @PathVariable UUID id,
            @RequestBody Category category
    ) {
        Category updatedCategory = categoryService.update(id, category);

        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable UUID id
    ) {
        categoryService.delete(id);
    }
}
