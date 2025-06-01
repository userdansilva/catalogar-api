package com.catalogar.productType;

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
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {
    private final UserService userService;
    private final ProductTypeService productTypeService;
    private final ProductTypeMapper productTypeMapper;
    private final Validator validator;

    public ProductTypeController(UserService userService, ProductTypeService productTypeService, ProductTypeMapper productTypeMapper, Validator validator) {
        this.userService = userService;
        this.productTypeService = productTypeService;
        this.productTypeMapper = productTypeMapper;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductTypeDto>>> getAll(
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

        ProductTypeFilter filter = new ProductTypeFilter(sort, field, page, perPage);

        var violations = validator.validate(filter);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Page<ProductType> productTypes = productTypeService.getAll(filter, user);

        return ResponseEntity.ok()
                .body(productTypeMapper.toApiResponse(productTypes));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductTypeDto>> getById(
        @AuthenticationPrincipal Jwt jwt,
        @PathVariable("id") UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        ProductType productType = productTypeService.getById(id, user);

        return ResponseEntity.ok()
                .body(productTypeMapper.toApiResponse(productType));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductTypeDto>> create(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ProductTypeRequest request
    ) {
       User user = userService.getByJwtOrCreate(jwt);

       ProductType productType = productTypeService.create(request, user);
       URI location = URI.create("/api/v1/product-types/" + productType.getId());

       return ResponseEntity.created(location)
               .body(productTypeMapper.toApiResponse(productType));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<ProductTypeDto>> update(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ProductTypeRequest request,
            @PathVariable("id") UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);
        ProductType productType = productTypeService.update(id, request, user);

        return ResponseEntity.ok()
                .body(productTypeMapper.toApiResponse(productType));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        productTypeService.delete(id, user);

        return ResponseEntity.noContent()
                .build();
    }

}
