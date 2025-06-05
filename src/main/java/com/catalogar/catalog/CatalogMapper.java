package com.catalogar.catalog;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.company.CompanyDto;
import com.catalogar.company.CompanyMapper;
import com.catalogar.theme.ThemeDto;
import com.catalogar.theme.ThemeMapper;
import com.catalogar.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class CatalogMapper {
    private final CompanyMapper companyMapper;
    private final ThemeMapper themeMapper;

    public CatalogMapper(CompanyMapper companyMapper, ThemeMapper themeMapper) {
        this.companyMapper = companyMapper;
        this.themeMapper = themeMapper;
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

    public Catalog toCatalog(User user, CreateCatalogRequest request) {
        return new Catalog(
                user,
                request.name()
        );
    }

    public ApiResponse<CatalogDto> toApiResponse(Catalog catalog) {
        return this.toApiResponse(catalog, null);
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
