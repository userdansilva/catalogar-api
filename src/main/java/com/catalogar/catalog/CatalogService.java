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
        userService.updateCurrentCatalog(user, catalog);

        return catalog;
    }

    public Catalog update(UpdateCatalogRequest request, User user) {
        Catalog currentCatalog = getCurrentCatalogByUser(user);

        boolean isSameSlug = currentCatalog.getSlug()
                .equals(request.slug());

        if (isSameSlug) return currentCatalog;

        boolean existsBySlugAndIdNot = catalogRepository
                .existsBySlugAndIdNot(
                        request.slug(),
                        user.getCurrentCatalog().getId());

        if (existsBySlugAndIdNot) {
            throw new UniqueFieldConflictException(
                    "Catálogo com o slug: " + request.slug() + " já está cadastrado"
            );
        }

        currentCatalog.setSlug(request.slug());

        return catalogRepository.save(currentCatalog);
    }

    private Catalog getCurrentCatalogByUser(User user) {
        return getByIdAndUser(user.getCurrentCatalog().getId(), user);
    }

    public Catalog getByIdAndUser(UUID id, User user) {
        return catalogRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Catálogo com id: " + id + " não encontrado"
                ));

    }
}
