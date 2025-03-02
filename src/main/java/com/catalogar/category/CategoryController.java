package com.catalogar.category;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final Validator validator;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, Validator validator, CategoryMapper categoryMapper, UserService userService) {
        this.categoryService = categoryService;
        this.validator = validator;
        this.categoryMapper = categoryMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAll(
            @AuthenticationPrincipal Jwt jwt,
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
        User user = userService.getByJwtOrCreate(jwt);

        CategoryFilter filter = new CategoryFilter(sort, field, page, perPage);

        var violations = validator.validate(filter);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Page<Category> categories = categoryService.getAll(filter, user);

        return ResponseEntity.ok()
                .body(categoryMapper.toApiResponse(categories));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Category category = categoryService.getById(id, user);

        return ResponseEntity.ok()
                .body(categoryMapper.toApiResponse(category));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> create(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CategoryRequest request
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Category category = categoryService.create(request, user);
        URI location = URI.create("/api/v1/categories/" + category.getId());

        return ResponseEntity.created(location)
                .body(categoryMapper.toApiResponse(category));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> update(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CategoryRequest request,
            @PathVariable UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);
        Category category = categoryService.update(id, request, user);

        return ResponseEntity.ok()
                .body(categoryMapper.toApiResponse(category));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        categoryService.delete(id, user);

        return ResponseEntity.noContent()
                .build();
    }
}
