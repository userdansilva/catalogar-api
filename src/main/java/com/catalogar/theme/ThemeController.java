package com.catalogar.theme;

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
@RequestMapping("/api/v1/themes")
public class ThemeController {
    private final UserService userService;
    private final ThemeService themeService;
    private final ThemeMapper themeMapper;

    public ThemeController(UserService userService, ThemeService themeService, ThemeMapper themeMapper) {
        this.userService = userService;
        this.themeService = themeService;
        this.themeMapper = themeMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ThemeDto>> create(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ThemeRequest request
    ) {
        User user =  userService.getByJwtOrCreate(jwt);

        Theme theme = themeService.create(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(themeMapper.toApiResponse(theme));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ThemeDto>> update(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ThemeRequest request
    ) {
        User user = userService.getByJwtOrCreate(jwt);

        Theme theme = themeService.update(request, user);

        return ResponseEntity.ok()
                .body(themeMapper.toApiResponse(theme));
    }
}
