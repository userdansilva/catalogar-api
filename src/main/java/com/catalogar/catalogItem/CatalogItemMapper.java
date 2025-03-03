package com.catalogar.catalogItem;

import com.catalogar.category.CategoryDto;
import com.catalogar.category.CategoryMapper;
import com.catalogar.common.dto.ApiResponse;
import com.catalogar.product.ProductDto;
import com.catalogar.product.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogItemMapper {
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public CatalogItemMapper(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    public CatalogItem toCatalogItem(CatalogItemRequest request) {
        return new CatalogItem(
                request.title(),
                request.price()
        );
    }

    public ApiResponse<CatalogItemDto> toApiResponse(CatalogItem catalogItem) {
        CatalogItemDto catalogItemDto = this.toDto(catalogItem);

        return new ApiResponse<>(catalogItemDto);
    }

    public CatalogItemDto toDto(CatalogItem catalogItem) {
        ProductDto productDto = productMapper.toDto(catalogItem
                .getProduct());

        List<CategoryDto> categoryDtoList = catalogItem.getCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return new CatalogItemDto(
                catalogItem.getId(),
                catalogItem.getTitle(),
                catalogItem.getPrice(),
                catalogItem.getReference(),
                productDto,
                categoryDtoList,
                catalogItem.getDisabledAt() != null,
                catalogItem.getDisabledAt(),
                catalogItem.getCreatedAt(),
                catalogItem.getUpdatedAt()
        );
    }
}
