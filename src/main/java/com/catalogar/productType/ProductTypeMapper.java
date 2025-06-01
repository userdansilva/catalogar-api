package com.catalogar.productType;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.common.dto.Metadata;
import com.catalogar.common.dto.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductTypeMapper {

    public ProductType toProductType(ProductTypeRequest request) {
        return new ProductType(request.name(), request.slug());
    }

    public ProductTypeDto toDto(ProductType productType) {
        return new ProductTypeDto(
                productType.getId(),
                productType.getName(),
                productType.getSlug(),
                productType.getDisabledAt() != null,
                productType.getDisabledAt(),
                productType.getCreatedAt(),
                productType.getUpdatedAt()
        );
    }

    public ApiResponse<ProductTypeDto> toApiResponse(ProductType productType) {
        return new ApiResponse<>(this.toDto(productType));
    }

    public Sort toSort(ProductTypeFilter filter) {
        Sort.Direction direction = Sort.Direction.valueOf(
                Sort.Direction.class,
                filter.order().toUpperCase()
        );

        return Sort.by(direction, filter.field());
    }

    public ApiResponse<List<ProductTypeDto>> toApiResponse(Page<ProductType> productTypes) {
        Pagination pagination = new Pagination(
                productTypes.getNumber() + 1,
                productTypes.getSize(),
                productTypes.getTotalPages(),
                (int) productTypes.getTotalElements()
        );

        Metadata meta = new Metadata(pagination);

        return new ApiResponse<>(
                productTypes.stream()
                        .map(this::toDto)
                        .toList(),
                meta
        );
    }
}
