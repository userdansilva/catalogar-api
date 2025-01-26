package com.catalogar.service;

import com.catalogar.dto.CatalogRequestDto;
import com.catalogar.exception.ResourceNotFoundException;
import com.catalogar.exception.UniqueFieldConflictException;
import com.catalogar.mapper.CatalogMapper;
import com.catalogar.model.Catalog;
import com.catalogar.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    public CatalogService(CatalogRepository catalogRepository, CatalogMapper catalogMapper) {
        this.catalogRepository = catalogRepository;
        this.catalogMapper = catalogMapper;
    }

    public Catalog getById(UUID id) {
        return catalogRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                   "Catálogo com id: " + id + " não encontrado"
                ));
    }

    public Catalog create(CatalogRequestDto requestDto) {
        boolean existsBySlug = catalogRepository
                .existsBySlug(requestDto.slug());

        if (existsBySlug) {
            throw new UniqueFieldConflictException(
                    "Catálogo com o slug: " + requestDto.slug() + " já está cadastrado"
            );
        }

        Catalog catalog = catalogMapper.toCatalog(requestDto);

        return catalogRepository.save(catalog);
    }
}
