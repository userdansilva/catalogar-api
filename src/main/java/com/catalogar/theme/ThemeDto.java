package com.catalogar.theme;

import com.catalogar.logo.LogoDto;

public record ThemeDto(
        String primaryColor,
        String secondaryColor,
        LogoDto logo
) {
}
