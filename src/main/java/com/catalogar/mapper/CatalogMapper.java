package com.catalogar.mapper;

import com.catalogar.dto.ApiResponse;
import com.catalogar.dto.CatalogDto;
import com.catalogar.dto.CatalogRequestDto;
import com.catalogar.dto.CompanyDto;
import com.catalogar.model.Catalog;
import com.catalogar.model.User;
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

    public Catalog toCatalog(User user, CatalogRequestDto catalogRequestDto) {
        LocalDateTime publishedAt = catalogRequestDto.isActive()
                ? ZonedDateTime.now().toLocalDateTime()
                : null;

        return new Catalog(
                user,
                catalogRequestDto.slug(),
                publishedAt
        );
    }

    public Catalog toCatalog(CatalogRequestDto requestDto) {
        return this.toCatalog(null, requestDto);
    }

    public ApiResponse<CatalogDto> toApiResponse(Catalog catalog) {
        return new ApiResponse<CatalogDto>(this.toDto(catalog));
    }

    public CatalogDto toDto(Catalog catalog) {
        CompanyDto companyDto = Optional.ofNullable(catalog.getCompany())
                .map(companyMapper::toDto)
                .orElse(null);

        boolean isPublished = catalog.getPublishedAt() != null;

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
