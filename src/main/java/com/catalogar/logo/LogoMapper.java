package com.catalogar.logo;

import org.springframework.stereotype.Component;

@Component
public class LogoMapper {
    public LogoDto toDto(Logo logo) {
        return new LogoDto(
                logo.getId(),
                logo.getName(),
                logo.getUrl(),
                logo.getWidth(),
                logo.getHeight(),
                logo.getCreatedAt()
        );
    }
}
