package com.catalogar.catalogItem;

import com.catalogar.catalog.Catalog;
import com.catalogar.catalogItemImage.CatalogItemImage;
import com.catalogar.catalogItemImage.CatalogItemImageRequest;
import com.catalogar.category.Category;
import com.catalogar.category.CategoryService;
import com.catalogar.common.exception.BadRequestException;
import com.catalogar.common.exception.ResourceNotFoundException;
import com.catalogar.common.message.MessageService;
import com.catalogar.productType.ProductType;
import com.catalogar.productType.ProductTypeService;
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
    private final ProductTypeService productTypeService;
    private final MessageService messageService;
    private final CategoryService categoryService;
    private final CatalogItemMapper catalogItemMapper;
    private final CatalogItemRepository catalogItemRepository;
    private final StorageService storageService;

    public CatalogItemService(UserService userService, ProductTypeService productTypeService, MessageService messageService, CategoryService categoryService, CatalogItemMapper catalogItemMapper, CatalogItemRepository catalogItemRepository, StorageService storageService) {
        this.userService = userService;
        this.productTypeService = productTypeService;
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
        UUID productTypeId = UUID.fromString(request.productTypeId());
        List<UUID> categoryIds = request.categoryIds() != null
                ? toUUIDList(request.categoryIds())
                : new ArrayList<>();

        CatalogItem catalogItem = catalogItemMapper.toCatalogItem(request);

        ProductType productType = getProductTypeById(productTypeId, catalog);
        List<Category> categories = getCategoriesByIds(categoryIds, catalog);
        Long reference = generateUniqueReference(catalog);
        LocalDateTime disabledAt = request.isDisabled() ? LocalDateTime.now() : null;

        catalogItem.setProductType(productType);
        catalogItem.setCategories(categories);
        catalogItem.setReference(reference);
        catalogItem.setDisabledAt(disabledAt);
        catalogItem.setCatalog(catalog);

        List<CatalogItemImage> images = toCatalogItemImages(request.images(), catalogItem, catalog);
        catalogItem.setImages(images);

        return catalogItemRepository.save(catalogItem);
    }

    private void validateImages(List<CatalogItemImageRequest> images) {
        List<String> imageNames = images.stream()
                .map(CatalogItemImageRequest::fileName)
                .toList();

        validateUnique(imageNames);

        List<Integer> imagePositions = images.stream()
                .map(CatalogItemImageRequest::position)
                .toList();

        validateImagePositionSequenceAndOrder(imagePositions);

        List<String> imageUrls = images.stream()
                .map(CatalogItemImageRequest::url)
                .toList();

        validateImageUrls(imageUrls);
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

    private void validateImageUrls(List<String> imageUrls) {
        String blobUrl = storageService.getBlobUrl();

        // Add mais URLs válida caso necessário
        List<String> validUrls = List.of(blobUrl);

        for (String imageUrl : imageUrls) {
            boolean isValid = validUrls.stream().anyMatch(imageUrl::startsWith);

            if (!isValid) {
                throw new BadRequestException(
                        messageService.getMessage("error.image.invalid_url")
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

    private ProductType getProductTypeById(UUID id, Catalog catalog) {
        return productTypeService.findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new BadRequestException(
                        messageService.getMessage("error.catalog_item.product_type_not_found")
                ));
    }

    private Category getCategoryById(UUID id, Catalog catalog) {
        return categoryService.findByIdAndCatalog(id, catalog)
                .orElseThrow(() -> new BadRequestException(
                        messageService.getMessage("error.catalog_item.category_not_found")
                ));
    }

    private List<CatalogItemImage> toCatalogItemImages(
            List<CatalogItemImageRequest> imageRequests,
            CatalogItem catalogItem,
            Catalog catalog
    ) {
        return imageRequests.stream()
                .map((imageRequest -> {
                    String name = imageRequest.fileName();
                    String url = imageRequest.url();
                    Long size = (long) imageRequest.sizeInBytes();
                    short width = (short) imageRequest.width();
                    short height = (short) imageRequest.height();
                    String altText = imageRequest.altText();
                    short position = (short) imageRequest.position();

                    return new CatalogItemImage(
                            name,
                            url,
                            size,
                            catalog,
                            width,
                            height,
                            altText,
                            position,
                            catalogItem
                    );
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
        validateImages(request.images());

        Catalog catalog = getUserCurrentCatalog(user);
        UUID productTypeId = UUID.fromString(request.productTypeId());
        List<UUID> categoryIds = request.categoryIds() != null
                ? toUUIDList(request.categoryIds())
                : new ArrayList<>();

        CatalogItem catalogItem = getById(id, user);

        ProductType productType = getProductTypeById(productTypeId, catalog);
        List<Category> categories = getCategoriesByIds(categoryIds, catalog);
        LocalDateTime disabledAt = request.isDisabled() ? LocalDateTime.now() : null;

        catalogItem.setTitle(request.title());
        catalogItem.setPrice(request.price());
        catalogItem.setProductType(productType);
        catalogItem.setCategories(categories);
        catalogItem.setDisabledAt(disabledAt);

        List<CatalogItemImage> images = toCatalogItemImages(request.images(), catalogItem, catalog);

        List<CatalogItemImage> newImages = images.stream().map(newImage -> {
            Optional<CatalogItemImage> existingImage = catalogItem.getImages().stream()
                    .filter(image -> newImage.getUrl().equals(image.getUrl()))
                    .findFirst();

            return existingImage.map(existing -> {
                existing.setPosition(newImage.getPosition());
                return existing;
            }).orElse(newImage);
        }).toList();

        catalogItem.getImages().clear();
        catalogItem.getImages().addAll(newImages);

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
