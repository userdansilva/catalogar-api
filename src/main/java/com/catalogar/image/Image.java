package com.catalogar.image;

import com.catalogar.catalog.Catalog;
import com.catalogar.file.File;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity(name = "Image")
@EntityListeners(AuditingEntityListener.class)
public class Image extends File {
    @Column(
            name = "width",
            updatable = false,
            columnDefinition = "SMALLINT"
    )
    private Short width;

    @Column(
            name = "height",
            updatable = false,
            columnDefinition = "SMALLINT"
    )
    private Short height;

    @Column(
            name = "alt_text",
            columnDefinition = "TEXT"
    )
    private String altText;

    public Image(
            String name,
            String url,
            Long size,
            Catalog catalog,
            Short width,
            Short height,
            String altText
    ) {
        super(name, url, size, catalog);
        this.width = width;
        this.height = height;
        this.altText = altText;
    }

    public Image() {
    }

    public Short getWidth() {
        return width;
    }

    public void setWidth(Short width) {
        this.width = width;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Image image = (Image) o;
        return Objects.equals(width, image.width) && Objects.equals(height, image.height) && Objects.equals(altText, image.altText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height, altText);
    }

    @Override
    public String toString() {
        return "Image{" +
                "width=" + width +
                ", height=" + height +
                ", altText='" + altText + '\'' +
                "} " + super.toString();
    }
}
