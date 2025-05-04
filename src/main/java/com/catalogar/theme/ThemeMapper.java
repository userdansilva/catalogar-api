package com.catalogar.theme;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.logo.LogoDto;
import com.catalogar.logo.LogoMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ThemeMapper {
    private final LogoMapper logoMapper;

    public ThemeMapper(LogoMapper logoMapper) {
        this.logoMapper = logoMapper;
    }

    public Theme toTheme(ThemeRequest request) {
        return new Theme(
                request.primaryColor(),
                request.secondaryColor()
        );
    }

    public ApiResponse<ThemeDto> toApiResponse(Theme theme) {
        return new ApiResponse<>(this.toDto(theme));
    }

    public ThemeDto toDto(Theme theme) {
        LogoDto logo = Optional.ofNullable(theme.getLogo())
                .map(logoMapper::toDto)
                .orElse(null);

        return new ThemeDto(
                theme.getPrimaryColor(),
                theme.getSecondaryColor(),
                logo
        );
    }
}
