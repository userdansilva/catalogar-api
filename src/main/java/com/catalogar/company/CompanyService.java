package com.catalogar.company;

import com.catalogar.catalog.Catalog;
import com.catalogar.common.exception.UniqueFieldConflictException;
import com.catalogar.common.message.MessageService;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final UserService userService;
    private final MessageService messageService;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(UserService userService, MessageService messageService, CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.userService = userService;
        this.messageService = messageService;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public Company create(CreateCompanyRequest request, User user) {
        Catalog currentCatalog = getUserCurrentCatalog(user);
        boolean hasCompany = currentCatalog.hasCompany();

        if (hasCompany) {
            throw new UniqueFieldConflictException(
                    messageService.getMessage("error.company.already_created")
            );
        }

        return createCompany(request, currentCatalog);
    }

    private Catalog getUserCurrentCatalog(User user) {
        return userService.getUserCurrentCatalog(user);
    }

    private Company createCompany(CreateCompanyRequest request, Catalog catalog) {
        Company company = companyMapper.toCompany(request);
        company.setCatalog(catalog);

        return companyRepository.save(company);
    }
}
