package com.catalogar.catalogItem;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/catalog-items")
public class CatalogItemController {
    private final UserService userService;
    private final CatalogItemService catalogItemService;
    private final CatalogItemMapper catalogItemMapper;

    public CatalogItemController(UserService userService, CatalogItemService catalogItemService, CatalogItemMapper catalogItemMapper) {
        this.userService = userService;
        this.catalogItemService = catalogItemService;
        this.catalogItemMapper = catalogItemMapper;
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
}
