package com.catalogar.user;

import com.catalogar.catalog.Catalog;
import com.catalogar.catalog.CatalogService;
import com.catalogar.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());

        return ResponseEntity.ok()
                .body(userMapper.toApiResponse(user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(
            @Valid @RequestBody CreateUserRequest userRequestDto
    ) {
         User user = userService.create(userRequestDto);

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
