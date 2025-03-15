package com.catalogar.catalog;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.common.message.MessageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalogs")
public class CatalogController {
    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;
    private final MessageService messageService;
    private final UserService userService;

    public CatalogController(
            CatalogService catalogService,
            CatalogMapper catalogMapper, MessageService messageService,
            UserService userService
    ) {
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CatalogDto>> create(
            @Valid @RequestBody CatalogRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Catalog catalog = catalogService.create(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catalogMapper.toApiResponse(
                        catalog,
                        messageService.getMessage(
                                "success.catalog.created",
                                catalog.getName())
                ));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CatalogDto>> update(
            @Valid @RequestBody CatalogRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Catalog catalog = catalogService.update(request, user);

        return ResponseEntity.ok()
                .body(catalogMapper.toApiResponse(
                        catalog,
                        messageService.getMessage(
                                "success.catalog.updated",
                                catalog.getName())
                ));
    }
}
