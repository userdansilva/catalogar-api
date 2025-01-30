package com.catalogar.controller;

import com.catalogar.mapper.CatalogMapper;
import com.catalogar.service.CatalogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController {
    private final CatalogService catalogService;
    private final CatalogMapper catalogMapper;

    public CatalogController(
            CatalogService catalogService,
            CatalogMapper catalogMapper
    ) {
        this.catalogService = catalogService;
        this.catalogMapper = catalogMapper;
    }

}
