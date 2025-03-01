package com.catalogar.product;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.common.dto.Metadata;
import com.catalogar.common.dto.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequest request) {
        return new Product(request.name(), request.slug());
    }

    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getName(),
                product.getSlug(),
                product.getDisabledAt() == null,
                product.getDisabledAt(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public ApiResponse<ProductDto> toApiResponse(Product product) {
        return new ApiResponse<>(this.toDto(product));
    }

    public Sort toSort(ProductFilter filter) {
        Sort.Direction direction = Sort.Direction.valueOf(
                Sort.Direction.class,
                filter.order().toUpperCase()
        );

        return Sort.by(direction, filter.field());
    }

    public ApiResponse<List<ProductDto>> toApiResponse(Page<Product> products) {
        Pagination pagination = new Pagination(
                products.getNumber() + 1,
                products.getSize(),
                products.getTotalPages(),
                (int) products.getTotalElements()
        );

        Metadata meta = new Metadata(pagination);

        return new ApiResponse<>(
                products.stream()
                        .map(this::toDto)
                        .toList(),
                meta
        );
    }
}
