package com.catalogar.product;

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

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final UserService userService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final Validator validator;

    public ProductController(UserService userService, ProductService productService, ProductMapper productMapper, Validator validator) {
        this.userService = userService;
        this.productService = productService;
        this.productMapper = productMapper;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAll(
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

        ProductFilter filter = new ProductFilter(sort, field, page, perPage);

        var violations = validator.validate(filter);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Page<Product> products = productService.getAll(filter, user);

        return ResponseEntity.ok()
                .body(productMapper.toApiResponse(products));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> create(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ProductRequest request
    ) {
       User user = userService.getByJwtOrCreate(jwt);

       Product product = productService.create(request, user);
       URI location = URI.create("/api/v1/products/" + product.getId());

       return ResponseEntity.created(location)
               .body(productMapper.toApiResponse(product));
    }
}
