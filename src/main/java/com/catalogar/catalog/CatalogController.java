package com.catalogar.catalog;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());

        Catalog catalog = catalogService.create(request, user);

        return ResponseEntity.ok()
                .body(catalogMapper.toApiResponse(catalog));
    }

    // Publish Catalog

    // Update Slug
}
