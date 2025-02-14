package com.catalogar.company;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyRequest request) {
        return new Company(
                request.name(),
                request.mainSiteUrl(),
                request.phoneNumber(),
                request.businessTypeDescription()
        );
    }

    public ApiResponse<CompanyDto> toApiResponse(Company company) {
        return new ApiResponse<>(this.toDto(company));
    }

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
