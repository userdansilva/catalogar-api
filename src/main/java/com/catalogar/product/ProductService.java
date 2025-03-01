package com.catalogar.product;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        product.setCatalog(catalog);

        return productRepository.save(product);
    }

    private boolean existsByNameAndCatalog(String name, Catalog catalog) {
        return productRepository.existsByNameAndCatalog(name, catalog);
    }

    private boolean existsBySlugAndCatalog(String slug, Catalog catalog) {
        return productRepository.existsBySlugAndCatalog(slug, catalog);
    }
}
