package com.catalogar.category;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;
    private final MessageService messageService;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, UserService userService, MessageService messageService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.userService = userService;
        this.messageService = messageService;
    }

    public Page<Category> getAll(CategoryFilter filterDto, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        int pageNumber = Integer.parseInt(filterDto.page()) - 1;
        int pageSize = Integer.parseInt(filterDto.perPage());

        Sort sort = categoryMapper.toSort(filterDto);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return categoryRepository.findAllByCatalog(catalog, pageable);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    public Category getById(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);

        return findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("error.category.not_found")
                ));
    }

    public Optional<Category> findByIdAndCatalog(UUID id, Catalog catalog) {
        return categoryRepository.findByIdAndCatalog(id, catalog);
    }

    public Category create(CategoryRequest request, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsByName = existsByNameAndCatalog(request.name(), catalog);

        if (existsByName) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.category.name_unavailable", request.name())
            );
        }

        boolean existsBySlug = existsBySlugAndCatalog(request.slug(), catalog);

        if (existsBySlug) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.category.slug_unavailable", request.slug())
            );
        }

        Category category = categoryMapper.toCategory(request);
        category.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        category.setCatalog(catalog);

        return categoryRepository.save(category);
    }

    private boolean existsByNameAndCatalog(String name, Catalog catalog) {
        return categoryRepository.existsByNameAndCatalog(name, catalog);
    }

    private boolean existsBySlugAndCatalog(String slug, Catalog catalog) {
        return categoryRepository.existsBySlugAndCatalog(slug, catalog);
    }

    public Category update(UUID id, CategoryRequest request, User user) {
        Category category = getById(id, user);
        Catalog catalog = getUserCurrentCatalog(user);

        boolean existsByNameAndIdNot = existsByNameAndIdNotAndCatalog(
                request.name(), id, catalog
        );

        if (existsByNameAndIdNot) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.category.name_unavailable", request.name())
            );
        }

        boolean existsBySlugAndIdNot = existsBySlugAndIdNotAndCatalog(
                request.slug(), id, catalog
        );

        if (existsBySlugAndIdNot) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.category.slug_unavailable", request.slug())
            );
        }

        category.setName(request.name());
        category.setSlug(request.slug());
        category.setTextColor(request.textColor());
        category.setBackgroundColor(request.backgroundColor());
        category.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        return categoryRepository.save(category);
    }

    private boolean existsByNameAndIdNotAndCatalog(String name, UUID id, Catalog catalog) {
        return categoryRepository.existsByNameAndIdNotAndCatalog(name, id, catalog);
    }

    private boolean existsBySlugAndIdNotAndCatalog(String slug, UUID id, Catalog catalog) {
        return categoryRepository.existsBySlugAndIdNotAndCatalog(slug, id, catalog);
    }

    public void delete(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsById = existsByIdAndCatalog(id, catalog);

        if (!existsById) {
            throw new ResourceNotFoundException(
                    messageService.getMessage("error.category.not_found")
            );
        }

        deleteByIdAndCatalog(id, catalog);
    }

    private boolean existsByIdAndCatalog(UUID id, Catalog catalog) {
        return categoryRepository.existsByIdAndCatalog(id, catalog);
    }

    private void deleteByIdAndCatalog(UUID id, Catalog catalog) {
        categoryRepository.deleteByIdAndCatalog(id, catalog);
    }

    public boolean existsByIdsAndCatalog(List<UUID> ids, Catalog catalog) {
        if (ids.isEmpty()) return false;

        return categoryRepository
                .countByIdInAndCatalog(ids, catalog) == ids.size();
    }
}
