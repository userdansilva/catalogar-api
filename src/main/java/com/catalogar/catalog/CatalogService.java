package com.catalogar.catalog;

import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
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

    public Catalog create(CreateCatalogRequest request, User user) {
        boolean existsBySlug = catalogRepository
                .existsBySlug(request.slug());

        if (existsBySlug) {
            throw new UniqueFieldConflictException(
                    "Catálogo com o slug: " + request.slug() + " já está cadastrado"
            );
        }

        Catalog catalog = catalogRepository.save(catalogMapper
                .toCatalog(user, request));

        // Must set the new catalog as current catalog on creation
        userService.setCurrentCatalog(user, catalog);

        return catalog;
    }

    public Catalog update(UpdateCatalogRequest request, User user) {
        Catalog catalog = getByUser(user);

        boolean isSameSlug = catalog.getSlug()
                .equals(request.slug());

        if (isSameSlug) return catalog;

        boolean existsBySlugAndIdNot = catalogRepository
                .existsBySlugAndIdNot(
                        request.slug(),
                        user.getCurrentCatalog().getId());

        if (existsBySlugAndIdNot) {
            throw new UniqueFieldConflictException(
                    "Catálogo com o slug: " + request.slug() + " já está cadastrado"
            );
        }

        catalog.setSlug(request.slug());

        return catalogRepository.save(catalog);
    }

    private Catalog getByUser(User user) {
        UUID currentCatalogId = user.getCurrentCatalog().getId();

        return catalogRepository
                .findByIdAndUserId(currentCatalogId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Catálogo com id: " + currentCatalogId + " não encontrado"
                ));
    }
}
