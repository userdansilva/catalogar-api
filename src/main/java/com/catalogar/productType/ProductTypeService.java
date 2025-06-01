package com.catalogar.productType;

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
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final MessageService messageService;
    private final ProductTypeMapper productTypeMapper;
    private final UserService userService;

    public ProductTypeService(ProductTypeRepository productTypeRepository, MessageService messageService, ProductTypeMapper productTypeMapper, UserService userService) {
        this.productTypeRepository = productTypeRepository;
        this.messageService = messageService;
        this.productTypeMapper = productTypeMapper;
        this.userService = userService;
    }

    public Page<ProductType> getAll(ProductTypeFilter filter, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        int pageNumber = Integer.parseInt(filter.page()) - 1;
        int pageSize = Integer.parseInt(filter.perPage());

        Sort sort = productTypeMapper.toSort(filter);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return productTypeRepository.findAllByCatalog(catalog, pageable);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    public ProductType getById(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);

        return findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("error.product_type.not_found")
                ));
    }

    public Optional<ProductType> findByIdAndCatalog(UUID id, Catalog catalog) {
        return productTypeRepository.findByIdAndCatalog(id, catalog);
    }

    public ProductType create(ProductTypeRequest request, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsByName = existsByNameAndCatalog(request.name(), catalog);

        if (existsByName) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product_type.name_unavailable", request.name())
            );
        }

        boolean existsBySlug = existsBySlugAndCatalog(request.slug(), catalog);

        if (existsBySlug) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product_type.slug_unavailable", request.slug())
            );
        }

        ProductType productType = productTypeMapper.toProductType(request);
        productType.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        productType.setCatalog(catalog);

        return productTypeRepository.save(productType);
    }

    private boolean existsByNameAndCatalog(String name, Catalog catalog) {
        return productTypeRepository.existsByNameAndCatalog(name, catalog);
    }

    private boolean existsBySlugAndCatalog(String slug, Catalog catalog) {
        return productTypeRepository.existsBySlugAndCatalog(slug, catalog);
    }

    public ProductType update(UUID id, ProductTypeRequest request, User user) {
        ProductType productType = getById(id, user);
        Catalog catalog = getUserCurrentCatalog(user);

        boolean existsByNameAndIdNot = existsByNameAndIdNotAndCatalog(
                request.name(), id, catalog
        );

        if (existsByNameAndIdNot) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product_type.name_unavailable", request.name())
            );
        }

        boolean existsBySlugAndIdNot = existsBySlugAndIdNotAndCatalog(
                request.slug(), id, catalog
        );

        if (existsBySlugAndIdNot) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product_type.slug_unavailable", request.slug())
            );
        }

        productType.setName(request.name());
        productType.setSlug(request.slug());
        productType.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        return productTypeRepository.save(productType);
    }

    private boolean existsByNameAndIdNotAndCatalog(String name, UUID id, Catalog catalog) {
        return productTypeRepository.existsByNameAndIdNotAndCatalog(name, id, catalog);
    }

    private boolean existsBySlugAndIdNotAndCatalog(String slug, UUID id, Catalog catalog) {
        return productTypeRepository.existsBySlugAndIdNotAndCatalog(slug, id, catalog);
    }

    public void delete(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsById = existsByIdAndCatalog(id, catalog);

        if (!existsById) {
            throw new ResourceNotFoundException(
                    messageService.getMessage("error.product_type.not_found")
            );
        }

        deleteByIdAndCatalog(id, catalog);
    }

    public boolean existsByIdAndCatalog(UUID id, Catalog catalog) {
        return productTypeRepository.existsByIdAndCatalog(id, catalog);
    }

    private void deleteByIdAndCatalog(UUID id, Catalog catalog) {
        productTypeRepository.deleteByIdAndCatalog(id, catalog);
    }
}
