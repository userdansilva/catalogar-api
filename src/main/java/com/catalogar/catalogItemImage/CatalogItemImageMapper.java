package com.catalogar.catalogItemImage;

import org.springframework.stereotype.Component;

@Component
public class CatalogItemImageMapper {
    public CatalogItemImageDto toDto(CatalogItemImage image) {
        return new CatalogItemImageDto(
                image.getId(),
                image.getName(),
                image.getUrl(),
                image.getSize(),
                image.getWidth(),
                image.getHeight(),
                image.getAltText(),
                image.getPosition(),
                image.getCreatedAt()
        );
    }
}
