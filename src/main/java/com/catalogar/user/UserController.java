package com.catalogar.user;

import com.catalogar.catalog.Catalog;
import com.catalogar.catalog.CatalogService;
import com.catalogar.common.config.Utilities;
import com.catalogar.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
        // how to get the user email

        if (jwt == null) {
            System.out.println("jwt is null");
        } else {
            System.out.println(Utilities.filterClaims(jwt).get("sub"));
            System.out.println(Utilities.filterClaims(jwt).get("name"));
            System.out.println(Utilities.filterClaims(jwt).get("email"));
            System.out.println(jwt.getClaims());
        }

        User user = userService.getByEmail("daniel.sousa@catalogar.com.br");

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PutMapping("/me/current-catalog/{catalogId}")
    public ResponseEntity<ApiResponse<UserDto>> updateCurrentCatalog(
            @PathVariable UUID catalogId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());
        Catalog catalog = catalogService.getByIdAndUser(catalogId, user);

        User userWithUpdatedCurrentCatalog = userService.updateCurrentCatalog(user, catalog);

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(userWithUpdatedCurrentCatalog));
    }

}
