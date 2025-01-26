package com.catalogar.service;

import com.catalogar.dto.CompanyRequestDto;
import com.catalogar.mapper.CompanyMapper;
import com.catalogar.model.Catalog;
import com.catalogar.model.Company;
import com.catalogar.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Company create(CompanyRequestDto requestDto, Catalog catalog) {
        Company company = companyMapper.toCompany(requestDto, catalog);

        return companyRepository.save(company);
    }
}
