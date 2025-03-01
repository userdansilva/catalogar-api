package com.catalogar.company;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.user.User;
import com.catalogar.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/companies")
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
            @Valid @RequestBody CompanyRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Company company = companyService.create(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyMapper.toApiResponse(company));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CompanyDto>> update(
        @Valid @RequestBody CompanyRequest request,
        @AuthenticationPrincipal Jwt jwt
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Company company = companyService.update(request, user);

        return ResponseEntity.ok()
                .body(companyMapper.toApiResponse(company));
    }
}
