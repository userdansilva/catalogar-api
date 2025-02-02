package com.catalogar.category;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.common.dto.Metadata;
import com.catalogar.common.dto.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getTextColor(),
                category.getBackgroundColor(),
                category.getDisabledAt() == null,
                category.getDisabledAt(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public Category toCategory(CategoryRequestDto categoryRequestDto) {
        return new Category(
                categoryRequestDto.name(),
                categoryRequestDto.slug(),
                categoryRequestDto.textColor(),
                categoryRequestDto.backgroundColor()
        );
    }

    public Sort toSort(CategoryFilterDto categoryFilterDto) {
        Sort.Direction direction = Sort.Direction.valueOf(
                Sort.Direction.class,
                categoryFilterDto.order().toUpperCase());

        return Sort.by(direction, categoryFilterDto.field());
    }

    public ApiResponse<List<CategoryDto>> toApiResponse(Page<Category> categoryPage) {
        Pagination pagination = new Pagination(
                categoryPage.getNumber() + 1,
                categoryPage.getSize(),
                categoryPage.getTotalPages(),
                (int) categoryPage.getTotalElements()
        );

        Metadata meta = new Metadata(pagination);

        return new ApiResponse<List<CategoryDto>>(
                categoryPage.stream()
                        .map(this::toDto)
                        .toList(),
                meta
        );
    }

    public ApiResponse<CategoryDto> toApiResponse(Category category) {
        return this.toApiResponse(category, null);
    }

    public ApiResponse<CategoryDto> toApiResponse(Category category, String message ) {
        CategoryDto categoryDto = this.toDto(category);
        Metadata meta = new Metadata(message);

        return new ApiResponse<CategoryDto>(categoryDto, message);
    }
}
