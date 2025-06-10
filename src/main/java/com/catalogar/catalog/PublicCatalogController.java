package com.catalogar.catalog;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/catalogs")
public class PublicCatalogController {
    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;

    public PublicCatalogController(CatalogService catalogService, CatalogMapper catalogMapper) {
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
    }

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<PublishedCatalogDto>> getBySlug(
            @PathVariable("slug") String slug
    ) {
        Catalog catalog = catalogService.getBySlug(slug);

        return ResponseEntity.ok()
                .body(catalogMapper.toApiResponse(catalog));
    }
}
