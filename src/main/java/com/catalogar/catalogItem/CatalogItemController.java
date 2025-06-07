package com.catalogar.catalogItem;

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
@RequestMapping("/api/v1/catalog-items")
public class CatalogItemController {
    private final UserService userService;
    private final CatalogItemService catalogItemService;
    private final CatalogItemMapper catalogItemMapper;
    private final Validator validator;

    public CatalogItemController(UserService userService, CatalogItemService catalogItemService, CatalogItemMapper catalogItemMapper, Validator validator) {
        this.userService = userService;
        this.catalogItemService = catalogItemService;
        this.catalogItemMapper = catalogItemMapper;
        this.validator = validator;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CatalogItemDto>>> getAll(
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
                    defaultValue = "16"
            )
            String perPage
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        CatalogItemFilter filter = new CatalogItemFilter(sort, field, page, perPage);

        var violations = validator.validate(filter);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Page<CatalogItem> catalogItems = catalogItemService.getAll(filter, user);

        return ResponseEntity.ok()
                .body(catalogItemMapper.toApiResponse(catalogItems));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CatalogItemDto>> getById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        CatalogItem catalogItem = catalogItemService.getById(id, user);

        return ResponseEntity.ok()
                .body(catalogItemMapper.toApiResponse(catalogItem));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CatalogItemDto>> create(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CatalogItemRequest request
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        CatalogItem catalogItem = catalogItemService.create(request, user);
        URI location = URI.create("/api/v1/catalog-items/" + catalogItem.getId());

        return ResponseEntity.created(location)
                .body(catalogItemMapper.toApiResponse(catalogItem));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CatalogItemDto>> update(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CatalogItemRequest request,
            @PathVariable UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);
        CatalogItem catalogItem = catalogItemService.update(id, request, user);

        return ResponseEntity.ok()
                .body(catalogItemMapper.toApiResponse(catalogItem));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID id
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        catalogItemService.delete(id, user);

        return ResponseEntity.noContent()
                .build();
    }
}
