package com.catalogar.controller;

import com.catalogar.dto.ApiResponse;
import com.catalogar.dto.CatalogDto;
import com.catalogar.dto.CatalogRequestDto;
import com.catalogar.mapper.CatalogMapper;
import com.catalogar.model.Catalog;
import com.catalogar.model.User;
import com.catalogar.service.CatalogService;
import com.catalogar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
            @Valid @RequestBody CatalogRequestDto catalogRequestDto
    ) {
        String email = "daniel.sousa@catalogar.com.br";
        User user = userService.getByEmail(email);

        Catalog catalog = catalogService.create(user, catalogRequestDto);

        return ResponseEntity.ok()
                .body(catalogMapper.toApiResponse(catalog));
    }
}
