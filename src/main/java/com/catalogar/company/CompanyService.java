package com.catalogar.company;

import com.catalogar.catalog.Catalog;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Company create(CreateCompanyRequest requestDto, Catalog catalog) {
        Company company = companyMapper.toCompany(requestDto, catalog);

        return companyRepository.save(company);
    }
}
