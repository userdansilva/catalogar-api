package com.catalogar.product;

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
public class ProductService {
    private final ProductRepository productRepository;
    private final MessageService messageService;
    private final ProductMapper productMapper;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, MessageService messageService, ProductMapper productMapper, UserService userService) {
        this.productRepository = productRepository;
        this.messageService = messageService;
        this.productMapper = productMapper;
        this.userService = userService;
    }

    public Page<Product> getAll(ProductFilter filter, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        int pageNumber = Integer.parseInt(filter.page()) - 1;
        int pageSize = Integer.parseInt(filter.perPage());

        Sort sort = productMapper.toSort(filter);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return productRepository.findAllByCatalog(catalog, pageable);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    public Product getById(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);

        return findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("error.product.not_found")
                ));
    }

    public Optional<Product> findByIdAndCatalog(UUID id, Catalog catalog) {
        return productRepository.findByIdAndCatalog(id, catalog);
    }

    public Product create(ProductRequest request, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsByName = existsByNameAndCatalog(request.name(), catalog);

        if (existsByName) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product.name_unavailable", request.name())
            );
        }

        boolean existsBySlug = existsBySlugAndCatalog(request.slug(), catalog);

        if (existsBySlug) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product.slug_unavailable", request.slug())
            );
        }

        Product product = productMapper.toProduct(request);
        product.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        product.setCatalog(catalog);

        return productRepository.save(product);
    }

    private boolean existsByNameAndCatalog(String name, Catalog catalog) {
        return productRepository.existsByNameAndCatalog(name, catalog);
    }

    private boolean existsBySlugAndCatalog(String slug, Catalog catalog) {
        return productRepository.existsBySlugAndCatalog(slug, catalog);
    }

    public Product update(UUID id, ProductRequest request, User user) {
        Product product = getById(id, user);
        Catalog catalog = getUserCurrentCatalog(user);

        boolean existsByNameAndIdNot = existsByNameAndIdNotAndCatalog(
                request.name(), id, catalog
        );

        if (existsByNameAndIdNot) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product.name_unavailable", request.name())
            );
        }

        boolean existsBySlugAndIdNot = existsBySlugAndIdNotAndCatalog(
                request.slug(), id, catalog
        );

        if (existsBySlugAndIdNot) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.product.slug_unavailable", request.slug())
            );
        }

        product.setName(request.name());
        product.setSlug(request.slug());
        product.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        return productRepository.save(product);
    }

    private boolean existsByNameAndIdNotAndCatalog(String name, UUID id, Catalog catalog) {
        return productRepository.existsByNameAndIdNotAndCatalog(name, id, catalog);
    }

    private boolean existsBySlugAndIdNotAndCatalog(String slug, UUID id, Catalog catalog) {
        return productRepository.existsBySlugAndIdNotAndCatalog(slug, id, catalog);
    }

    public void delete(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsById = existsByIdAndCatalog(id, catalog);

        if (!existsById) {
            throw new ResourceNotFoundException(
                    messageService.getMessage("error.product.not_found")
            );
        }

        deleteByIdAndCatalog(id, catalog);
    }

    public boolean existsByIdAndCatalog(UUID id, Catalog catalog) {
        return productRepository.existsByIdAndCatalog(id, catalog);
    }

    private void deleteByIdAndCatalog(UUID id, Catalog catalog) {
        productRepository.deleteByIdAndCatalog(id, catalog);
    }
}
