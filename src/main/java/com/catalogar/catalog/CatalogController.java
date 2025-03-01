package com.catalogar.catalog;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/catalogs")
public class CatalogController {
    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;

    private final UserService userService;

    public CatalogController(
            CatalogService catalogService,
            CatalogMapper catalogMapper,
            UserService userService
    ) {
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CatalogDto>> create(
            @Valid @RequestBody CreateCatalogRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Catalog catalog = catalogService.create(request, user);

        return ResponseEntity.ok()
                .body(catalogMapper.toApiResponse(catalog));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CatalogDto>> update(
            @Valid @RequestBody UpdateCatalogRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Catalog catalog = catalogService.update(request, user);

        return ResponseEntity.ok()
                .body(catalogMapper.toApiResponse(catalog));
    }
}
