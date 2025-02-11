package com.catalogar.company;

import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyDto toDto(Company company) {
        return new CompanyDto(
                company.getName(),
                company.getMainSiteUrl(),
                company.getPhoneNumber(),
                company.getBusinessTypeDescription(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }
}
