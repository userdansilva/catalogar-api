package com.catalogar.catalogItem;

import com.catalogar.catalog.Catalog;
import com.catalogar.category.Category;
import com.catalogar.category.CategoryService;
import com.catalogar.common.exception.BadRequestException;
import com.catalogar.common.message.MessageService;
import com.catalogar.product.Product;
import com.catalogar.product.ProductService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

@Service
public class CatalogItemService {
    private final UserService userService;
    private final ProductService productService;
    private final MessageService messageService;
    private final CategoryService categoryService;
    private final CatalogItemMapper catalogItemMapper;
    private final CatalogItemRepository catalogItemRepository;

    public CatalogItemService(UserService userService, ProductService productService, MessageService messageService, CategoryService categoryService, CatalogItemMapper catalogItemMapper, CatalogItemRepository catalogItemRepository) {
        this.userService = userService;
        this.productService = productService;
        this.messageService = messageService;
        this.categoryService = categoryService;
        this.catalogItemMapper = catalogItemMapper;
        this.catalogItemRepository = catalogItemRepository;
    }

    public CatalogItem create(
        CatalogItemRequest request,
        User user
    ) {
        Catalog catalog = getUserCurrentCatalog(user);
        UUID productId = UUID.fromString(request.productId());
        List<UUID> categoryIds = request.categoryIds() != null
                ? request.categoryIds()
                    .stream()
                    .map(UUID::fromString)
                    .toList()
                : new ArrayList<>();

        boolean productExistsById = productExistsById(productId, catalog);

        if (!productExistsById) {
            throw new BadRequestException(
                    messageService.getMessage("error.catalog_item.product_not_found")
            );
        }

        if (!categoryIds.isEmpty()) {
            boolean categoryExistsByIds = categoryExistsByIds(categoryIds, catalog);

            if (!categoryExistsByIds) {
                throw new BadRequestException(
                        messageService.getMessage("error.catalog_item.categories_not_found")
                );
            }
        }

        CatalogItem catalogItem = catalogItemMapper.toCatalogItem(request);
        catalogItem.setDisabledAt(request.isDisabled()
                ? LocalDateTime.now()
                : null);

        Product product = getProductById(productId, catalog);
        catalogItem.setProduct(product);
        catalogItem.setCatalog(catalog);

        categoryIds.forEach((categoryId) -> {
            Category category = getCategoryById(categoryId, catalog);
            catalogItem.getCategories().add(category);
        });

        catalogItem.setReference(generateUniqueReference(catalog));

        return catalogItemRepository.save(catalogItem);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    private boolean productExistsById(UUID id, Catalog catalog) {
        return productService.existsByIdAndCatalog(id, catalog);
    }

    private boolean categoryExistsByIds(List<UUID> ids, Catalog catalog) {
        return categoryService.existsByIdsAndCatalog(ids, catalog);
    }

    private Product getProductById(UUID id, Catalog catalog) {
        return productService.findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new BadRequestException(
                        messageService.getMessage("error.catalog_item.product_not_found")
                ));
    }

    private Category getCategoryById(UUID id, Catalog catalog) {
        return categoryService.findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new BadRequestException(
                        messageService.getMessage("error.catalog_item.category_not_found")
                ));
    }

    private Long generateUniqueReference(Catalog catalog) {
        Long reference;

        do {
            reference = generateRandomNumber();
        } while (catalogItemRepository.existsByReferenceAndCatalog(reference, catalog));

        return reference;
    }

    private Long generateRandomNumber() {
        Random random = new Random();
        int min = 100;
        int max = 999_999;

        return (long) (random.nextInt((max - min) + 1) + min);
    }
}
