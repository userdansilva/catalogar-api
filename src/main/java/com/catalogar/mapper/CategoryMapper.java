package com.catalogar.mapper;

import com.catalogar.dto.CategoryDto;
import com.catalogar.dto.CategoryFilterDto;
import com.catalogar.dto.CategoryRequestDto;
import com.catalogar.model.Category;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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
}
