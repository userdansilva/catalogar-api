package com.catalogar.mapper;

import com.catalogar.dto.*;
import com.catalogar.model.Category;
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

        return Sort.by(direction,categoryFilterDto.field());
    }

    public ApiResponseDto<List<CategoryDto>> toApiResponseDto(Page<Category> categoryPage) {
        PaginationMetadataDto pagination = new PaginationMetadataDto(
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalPages(),
                (int) categoryPage.getTotalElements()
        );

        return new ApiResponseDto<List<CategoryDto>>(
                categoryPage.stream()
                        .map(this::toDto)
                        .toList(),
                pagination
        );
    }
}
