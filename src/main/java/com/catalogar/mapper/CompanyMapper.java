package com.catalogar.mapper;

import com.catalogar.dto.CompanyRequestDto;
import com.catalogar.model.Catalog;
import com.catalogar.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toCompany(CompanyRequestDto requestDto, Catalog catalog) {
        return new Company(
                requestDto.name(),
                requestDto.siteUrl(),
                requestDto.phoneNumber(),
                requestDto.logoUrl(),
                catalog
        );
    }
}
