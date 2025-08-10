package com.catalogar.image;

import com.catalogar.catalog.Catalog;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageDto toDto(Image image) {
        return new ImageDto(
                image.getId(),
                image.getName(),
                image.getUrl(),
                image.getSize(),
                image.getWidth(),
                image.getHeight(),
                image.getAltText(),
                image.getCreatedAt()
        );
    }

    public Image toImage(ImageRequest request, Catalog catalog) {
        return new Image(
                request.fileName(),
                request.url(),
                (long) request.sizeInBytes(),
                catalog,
                (short) request.width(),
                (short) request.height(),
                request.altText()
        );
    }
}
