package com.catalogar.company;

import com.catalogar.catalog.Catalog;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toCompany(CreateCompanyRequest requestDto, Catalog catalog) {
        return new Company(
                requestDto.name(),
                requestDto.siteUrl(),
                requestDto.phoneNumber(),
                requestDto.logoUrl(),
                catalog
        );
    }

    public CompanyDto toDto(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getSiteUrl(),
                company.getLogoUrl(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }
}
