package com.catalogar.company;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {
    private final UserService userService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyController(UserService userService, CompanyService companyService, CompanyMapper companyMapper) {
        this.userService = userService;
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyDto>> create(
            @Valid @RequestBody CreateCompanyRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());

        Company company = companyService.create(request, user);

        return ResponseEntity.ok()
                .body(companyMapper.toApiResponse(company));
    }
}
