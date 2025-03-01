package com.catalogar.theme;

import com.catalogar.common.dto.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ThemeMapper {
    public Theme toTheme(ThemeRequest request) {
        return new Theme(
                request.primaryColor(),
                request.secondaryColor(),
                request.logoUrl()
        );
    }

    public ApiResponse<ThemeDto> toApiResponse(Theme theme) {
        return new ApiResponse<>(this.toDto(theme));
    }

    public ThemeDto toDto(Theme theme) {
        return new ThemeDto(
                theme.getPrimaryColor(),
                theme.getSecondaryColor(),
                theme.getLogoUrl()
        );
    }
}
