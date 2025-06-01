package com.catalogar.catalogItem;

import com.catalogar.category.CategoryDto;
import com.catalogar.category.CategoryMapper;
import com.catalogar.common.dto.ApiResponse;
import com.catalogar.common.dto.Metadata;
import com.catalogar.common.dto.Pagination;
import com.catalogar.image.ImageDto;
import com.catalogar.image.ImageMapper;
import com.catalogar.productType.ProductTypeDto;
import com.catalogar.productType.ProductTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogItemMapper {
    private final ProductTypeMapper productTypeMapper;
    private final CategoryMapper categoryMapper;
    private final ImageMapper imageMapper;

    public CatalogItemMapper(ProductTypeMapper productTypeMapper, CategoryMapper categoryMapper, ImageMapper imageMapper) {
        this.productTypeMapper = productTypeMapper;
        this.categoryMapper = categoryMapper;
        this.imageMapper = imageMapper;
    }

    public CatalogItem toCatalogItem(CatalogItemRequest request) {
        return new CatalogItem(
                request.title(),
                request.caption(),
                request.price()
        );
    }

    public ApiResponse<CatalogItemDto> toApiResponse(CatalogItem catalogItem) {
        CatalogItemDto catalogItemDto = this.toDto(catalogItem);

        return new ApiResponse<>(catalogItemDto);
    }

    public ApiResponse<List<CatalogItemDto>> toApiResponse(Page<CatalogItem> catalogItemPage) {
        Pagination pagination = new Pagination(
                catalogItemPage.getNumber() + 1,
                catalogItemPage.getSize(),
                catalogItemPage.getTotalPages(),
                (int) catalogItemPage.getTotalElements()
        );

        Metadata meta = new Metadata(pagination);

        return new ApiResponse<>(
                catalogItemPage.stream()
                        .map(this::toDto)
                        .toList(),
                meta
        );
    }

    public Sort toSort(CatalogItemFilter filter) {
        Sort.Direction direction = Sort.Direction.valueOf(
                Sort.Direction.class,
                filter.order().toUpperCase()
        );

        return Sort.by(direction, filter.field());
    }

    public CatalogItemDto toDto(CatalogItem catalogItem) {
        ProductTypeDto productTypeDto = productTypeMapper.toDto(catalogItem
                .getProductType());

        List<CategoryDto> categoryDtoList = catalogItem.getCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        List<ImageDto> imageDtoList = catalogItem.getImages()
                .stream()
                .map(imageMapper::toDto)
                .toList();


        return new CatalogItemDto(
                catalogItem.getId(),
                catalogItem.getTitle(),
                catalogItem.getCaption(),
                catalogItem.getPrice(),
                catalogItem.getReference(),
                productTypeDto,
                categoryDtoList,
                imageDtoList,
                catalogItem.getDisabledAt() != null,
                catalogItem.getDisabledAt(),
                catalogItem.getCreatedAt(),
                catalogItem.getUpdatedAt()
        );
    }
}
