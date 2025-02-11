package com.catalogar.catalog;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.company.CompanyDto;
import com.catalogar.company.CompanyMapper;
import com.catalogar.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class CatalogMapper {
    private final CompanyMapper companyMapper;

    public CatalogMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public Catalog toCatalog(User user, CreateCatalogRequest catalogRequestDto) {
        LocalDateTime publishedAt = catalogRequestDto.isPublished()
                ? ZonedDateTime.now().toLocalDateTime()
                : null;

        return new Catalog(
                user,
                catalogRequestDto.slug(),
                publishedAt
        );
    }

    public Catalog toCatalog(CreateCatalogRequest requestDto) {
        return this.toCatalog(null, requestDto);
    }

    public ApiResponse<CatalogDto> toApiResponse(Catalog catalog) {
        return new ApiResponse<CatalogDto>(this.toDto(catalog));
    }

    public CatalogDto toDto(Catalog catalog) {
        CompanyDto companyDto = Optional.ofNullable(catalog.getCompany())
                .map(companyMapper::toDto)
                .orElse(null);

        boolean isPublished = catalog.isPublished();

        return new CatalogDto(
                catalog.getId(),
                catalog.getSlug(),
                catalog.getPublishedAt(),
                isPublished,
                catalog.getCreatedAt(),
                catalog.getUpdatedAt(),
                companyDto
        );
    }
}
