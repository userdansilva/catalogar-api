package com.catalogar.service;

import com.catalogar.dto.CatalogRequestDto;
import com.catalogar.exception.ResourceNotFoundException;
import com.catalogar.exception.UniqueFieldConflictException;
import com.catalogar.mapper.CatalogMapper;
import com.catalogar.model.Catalog;
import com.catalogar.model.User;
import com.catalogar.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final UserService userService;

    public CatalogService(CatalogRepository catalogRepository, CatalogMapper catalogMapper, UserService userService) {
        this.catalogRepository = catalogRepository;
        this.catalogMapper = catalogMapper;
        this.userService = userService;
    }

    public Catalog getById(UUID id) {
        return catalogRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                   "Catálogo com id: " + id + " não encontrado"
                ));
    }

    public Catalog create(CatalogRequestDto requestDto, User user) {
        boolean existsBySlug = catalogRepository
                .existsBySlug(requestDto.slug());

        if (existsBySlug) {
            throw new UniqueFieldConflictException(
                    "Catálogo com o slug: " + requestDto.slug() + " já está cadastrado"
            );
        }

        Catalog catalog = catalogRepository.save(catalogMapper
                .toCatalog(user, requestDto));

        // Must set the new catalog as current catalog on creation
        userService.setCurrentCatalog(user, catalog);

        return catalog;
    }

    public boolean catalogBelongsToUser(Catalog catalog, User user) {
        return catalog.getUser().getId().equals(user.getId());
    }
}
