package com.catalogar.user;

import com.catalogar.catalog.Catalog;
import com.catalogar.catalog.CatalogService;
import com.catalogar.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final CatalogService catalogService;

    public UserController(
            UserService userService,
            UserMapper userMapper,
            CatalogService catalogService
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.catalogService = catalogService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getAuthenticatedUser(
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PutMapping("/me/current-catalog/{catalogId}")
    public ResponseEntity<ApiResponse<UserDto>> updateCurrentCatalog(
            @PathVariable UUID catalogId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);
        Catalog catalog = catalogService.getByIdAndUser(catalogId, user);

        User userWithUpdatedCurrentCatalog = userService.updateCurrentCatalog(user, catalog);

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(userWithUpdatedCurrentCatalog));
    }

}
