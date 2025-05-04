package com.catalogar.catalogItem;

import com.catalogar.catalog.Catalog;
import com.catalogar.category.Category;
import com.catalogar.category.CategoryService;
import com.catalogar.common.exception.BadRequestException;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.message.MessageService;
import com.catalogar.image.Image;
import com.catalogar.image.ImageRequest;
import com.catalogar.product.Product;
import com.catalogar.product.ProductService;
import com.catalogar.storage.StorageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CatalogItemService {
    private final UserService userService;
    private final ProductService productService;
    private final MessageService messageService;
    private final CategoryService categoryService;
    private final CatalogItemMapper catalogItemMapper;
    private final CatalogItemRepository catalogItemRepository;
    private final StorageService storageService;

    public CatalogItemService(UserService userService, ProductService productService, MessageService messageService, CategoryService categoryService, CatalogItemMapper catalogItemMapper, CatalogItemRepository catalogItemRepository, StorageService storageService) {
        this.userService = userService;
        this.productService = productService;
        this.messageService = messageService;
        this.categoryService = categoryService;
        this.catalogItemMapper = catalogItemMapper;
        this.catalogItemRepository = catalogItemRepository;
        this.storageService = storageService;
    }

    public Page<CatalogItem> getAll(CatalogItemFilter filter, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        int pageNumber = Integer.parseInt(filter.page()) - 1;
        int pageSize = Integer.parseInt(filter.perPage());

        Sort sort = catalogItemMapper.toSort(filter);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return catalogItemRepository.findAllByCatalog(catalog, pageable);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    public CatalogItem getById(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);

        return catalogItemRepository.findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageService.getMessage("error.catalog_item.not_found")
                ));
    }

    public CatalogItem create(
        CatalogItemRequest request,
        User user
    ) {
        validateImages(request.images());

        Catalog catalog = getUserCurrentCatalog(user);
        UUID productId = UUID.fromString(request.productId());
        List<UUID> categoryIds = request.categoryIds() != null
                ? toUUIDList(request.categoryIds())
                : new ArrayList<>();

        CatalogItem catalogItem = catalogItemMapper.toCatalogItem(request);

        Product product = getProductById(productId, catalog);
        List<Category> categories = getCategoriesByIds(categoryIds, catalog);
        Long reference = generateUniqueReference(catalog);
        LocalDateTime disabledAt = request.isDisabled() ? LocalDateTime.now() : null;

        catalogItem.setProduct(product);
        catalogItem.setCategories(categories);
        catalogItem.setReference(reference);
        catalogItem.setDisabledAt(disabledAt);
        catalogItem.setCatalog(catalog);

        List<Image> images = toImages(request.images(), catalogItem);
        catalogItem.setImages(images);

        return catalogItemRepository.save(catalogItem);
    }

    private void validateImages(List<ImageRequest> images) {
        List<String> imageNames = images.stream()
                .map(ImageRequest::name)
                .toList();

        validateUnique(imageNames);

        List<Integer> imagePositions = images.stream()
                .map(ImageRequest::position)
                .toList();

        validateImagePositionSequenceAndOrder(imagePositions);
    }

    private void validateUnique(List<String> imageNames) {
        Set<String> names = new HashSet<>();

        for (String imageName : imageNames) {
            if (!names.add(imageName)) {
                throw new BadRequestException(
                        messageService.getMessage("error.image.duplicated")
                );
            }
        }
    }

    private void validateImagePositionSequenceAndOrder(List<Integer> imagePositions) {
        List<Integer> sortedPositions = imagePositions.stream()
                .sorted()
                .toList();

        Set<Integer> positions = new HashSet<>();

        for (int i = 0; i < sortedPositions.size(); i++) {
            int position = sortedPositions.get(i);

            if (!positions.add(position)) {
                throw new BadRequestException(
                        messageService.getMessage("error.image.duplicated_position")
                );
            }

            if (position != i + 1) {
                throw new BadRequestException(
                        messageService.getMessage("error.image.invalid_position")
                );
            }
        }
    }

    private List<UUID> toUUIDList(List<String> values) {
        return values.stream().map(UUID::fromString).toList();
    }

    private List<Category> getCategoriesByIds(List<UUID> categoriesIds, Catalog catalog) {
        List<Category> categories = new ArrayList<>();

        categoriesIds.forEach((categoryId) -> {
            Category category = getCategoryById(categoryId, catalog);
            categories.add(category);
        });

        return categories;
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

    private List<Image> toImages(List<ImageRequest> imageRequests, CatalogItem catalogItem) {
        String blobUrl = storageService.getBlobUrl();

        return imageRequests.stream()
                .map((imageRequest -> {
                    String name = imageRequest.name();
                    String url = blobUrl + name;
                    Short position = (short) imageRequest.position();

                    return new Image(name, url, position, catalogItem);
                }))
                .toList();
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
        int min = 1_000;
        int max = 9_999;

        return (long) (random.nextInt((max - min) + 1) + min);
    }

    public CatalogItem update(UUID id, CatalogItemRequest request, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        UUID productId = UUID.fromString(request.productId());
        List<UUID> categoryIds = request.categoryIds() != null
                ? toUUIDList(request.categoryIds())
                : new ArrayList<>();

        CatalogItem catalogItem = getById(id, user);

        Product product = getProductById(productId, catalog);
        List<Category> categories = getCategoriesByIds(categoryIds, catalog);
        LocalDateTime disabledAt = request.isDisabled() ? LocalDateTime.now() : null;

        catalogItem.setTitle(request.title());
        catalogItem.setPrice(request.price());
        catalogItem.setProduct(product);
        catalogItem.setCategories(categories);
        catalogItem.setDisabledAt(disabledAt);

        return catalogItemRepository.save(catalogItem);
    }

    public void delete(UUID id, User user) {
        Catalog catalog = getUserCurrentCatalog(user);
        boolean existsById = existsByIdAndCatalog(id, catalog);

        if (!existsById) {
            throw new ResourceNotFoundException(
                    messageService.getMessage("error.catalog_item.not_found")
            );
        }

        deleteByIdAndCatalog(id, catalog);
    }

    private boolean existsByIdAndCatalog(UUID id, Catalog catalog) {
        return catalogItemRepository.existsByIdAndCatalog(id, catalog);
    }

    private void deleteByIdAndCatalog(UUID id, Catalog catalog) {
        catalogItemRepository.deleteByIdAndCatalog(id, catalog);
    }
}
