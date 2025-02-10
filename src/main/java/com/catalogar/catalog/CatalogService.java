package com.catalogar.catalog;

import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final UserService userService;
    private final MessageService messageService;

    public CatalogService(CatalogRepository catalogRepository,
                          CatalogMapper catalogMapper,
                          UserService userService,
                          MessageService messageService) {
        this.catalogRepository = catalogRepository;
        this.catalogMapper = catalogMapper;
        this.userService = userService;
        this.messageService = messageService;
    }

    public Catalog create(CreateCatalogRequest request, User user) {
        boolean existsBySlug = existsBySlug(request.slug());

        if (existsBySlug) {
            throw new UniqueFieldConflictException(messageService
                    .getMessage("error.catalog.slug_unavailable",
                            request.slug()));
        }

        return createAndUpdateUserCurrentCatalog(request, user);
    }

    private boolean existsBySlug(String slug) {
        return catalogRepository.existsBySlug(slug);
    }

    private Catalog createAndUpdateUserCurrentCatalog(CreateCatalogRequest request, User user) {
        Catalog catalog = createCatalog(request, user);
        updateUserCurrentCatalog(catalog, user);

        return catalog;
    }

    private Catalog createCatalog(CreateCatalogRequest request, User user) {
        return catalogRepository.save(catalogMapper
                .toCatalog(user, request));
    }

    private void updateUserCurrentCatalog(Catalog catalog, User user) {
        userService.updateCurrentCatalog(user, catalog);
    }

    public Catalog update(UpdateCatalogRequest request, User user) {
        Catalog currentCatalog = getUserCurrentCatalog(user);

        boolean existsBySlugAndIdNot = existsBySlug(request.slug(),
                currentCatalog.getId());

        if (existsBySlugAndIdNot) {
            throw new UniqueFieldConflictException(messageService
                    .getMessage("error.catalog.slug_unavailable",
                            request.slug()));
        }

        return updateCatalog(currentCatalog, request);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return user.getCurrentCatalog()
                .orElseThrow(() -> new ResourceNotFoundException(messageService
                        .getMessage("error.catalog.current_catalog_not_found")));
    }

    private boolean existsBySlug(String slug, UUID id) {
        return catalogRepository
                .existsBySlugAndIdNot(slug, id);
    }

    private Catalog updateCatalog(Catalog catalog, UpdateCatalogRequest request) {
        catalog.setSlug(request.slug());

        return catalogRepository.save(catalog);
    }

    public Catalog getByIdAndUser(UUID id, User user) {
        return getByIdAndUserId(id, user.getId());
    }

    private Catalog getByIdAndUserId(UUID id, UUID userId) {
        return catalogRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messageService
                        .getMessage("error.catalog.not_found")));
    }
}
