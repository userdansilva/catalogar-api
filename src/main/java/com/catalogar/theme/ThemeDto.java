package com.catalogar.theme;

import com.catalogar.image.ImageDto;

public record ThemeDto(
        String primaryColor,
        String secondaryColor,
        ImageDto logo
) {
}
