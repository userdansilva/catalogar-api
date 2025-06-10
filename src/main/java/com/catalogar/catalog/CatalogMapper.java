package com.catalogar.catalog;

import com.catalogar.catalogItem.CatalogItemDto;
import com.catalogar.catalogItem.CatalogItemMapper;
import com.catalogar.category.CategoryDto;
import com.catalogar.category.CategoryMapper;
import com.catalogar.common.dto.ApiResponse;
import com.catalogar.company.CompanyDto;
import com.catalogar.company.CompanyMapper;
import com.catalogar.productType.ProductTypeDto;
import com.catalogar.productType.ProductTypeMapper;
import com.catalogar.theme.ThemeDto;
import com.catalogar.theme.ThemeMapper;
import com.catalogar.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CatalogMapper {
    private final CompanyMapper companyMapper;
    private final ThemeMapper themeMapper;
    private final CategoryMapper categoryMapper;
    private final ProductTypeMapper productTypeMapper;
    private final CatalogItemMapper catalogItemMapper;

    public CatalogMapper(CompanyMapper companyMapper, ThemeMapper themeMapper, CategoryMapper categoryMapper, ProductTypeMapper productTypeMapper, CatalogItemMapper catalogItemMapper) {
        this.companyMapper = companyMapper;
        this.themeMapper = themeMapper;
        this.categoryMapper = categoryMapper;
        this.productTypeMapper = productTypeMapper;
        this.catalogItemMapper = catalogItemMapper;
    }

    public Catalog toCatalog(User user, UpdateCatalogRequest request) {
        LocalDateTime publishedAt = request.isPublished()
                ? ZonedDateTime.now().toLocalDateTime()
                : null;

        return new Catalog(
                user,
                request.name(),
                request.slug(),
                publishedAt
        );
    }

    public PublishedCatalogDto toPublishedDto(Catalog catalog) {
        CompanyDto company = companyMapper.toDto(catalog.getCompany());
        ThemeDto theme = themeMapper.toDto(catalog.getTheme());
        List<CategoryDto> categories = catalog.getCategories().stream()
                .map(categoryMapper::toDto)
                .toList();
        List<ProductTypeDto> productTypes = catalog.getProductTypes().stream()
                .map(productTypeMapper::toDto)
                .toList();
        List<CatalogItemDto> catalogItems = catalog.getCatalogItems().stream()
                .map(catalogItemMapper::toDto)
                .toList();


        return new PublishedCatalogDto(
                catalog.getId(),
                catalog.getName(),
                catalog.getSlug(),
                catalog.getPublishedAt(),
                catalog.isPublished(),
                catalog.getCreatedAt(),
                catalog.getUpdatedAt(),
                company,
                theme,
                categories,
                productTypes,
                catalogItems
        );
    }

    public Catalog toCatalog(User user, CreateCatalogRequest request) {
        return new Catalog(
                user,
                request.name()
        );
    }

    public ApiResponse<PublishedCatalogDto> toApiResponse(Catalog catalog) {
        return new ApiResponse<>(this.toPublishedDto(catalog));
    }

    public ApiResponse<CatalogDto> toApiResponse(Catalog catalog, String message) {
        return new ApiResponse<>(this.toDto(catalog), message);
    }

    public CatalogDto toDto(Catalog catalog) {
        Optional<CompanyDto> companyDto = Optional.ofNullable(catalog.getCompany())
                .map(companyMapper::toDto);

        Optional<ThemeDto> themeDto = Optional.ofNullable(catalog.getTheme())
                .map(themeMapper::toDto);

        boolean isPublished = catalog.isPublished();

        return new CatalogDto(
                catalog.getId(),
                catalog.getName(),
                Optional.ofNullable(catalog.getSlug()),
                Optional.ofNullable(catalog.getPublishedAt()),
                isPublished,
                catalog.getCreatedAt(),
                catalog.getUpdatedAt(),
                companyDto,
                themeDto
        );
    }
}
