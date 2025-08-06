package com.catalogar.theme;

import com.catalogar.common.dto.ApiResponse;
import com.catalogar.image.ImageDto;
import com.catalogar.image.ImageMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ThemeMapper {
    private final ImageMapper imageMapper;

    public ThemeMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
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
        ImageDto image = Optional.ofNullable(theme.getLogo())
                .map(imageMapper::toDto)
                .orElse(null);

        return new ThemeDto(
                theme.getPrimaryColor(),
                theme.getSecondaryColor(),
                image
        );
    }
}
