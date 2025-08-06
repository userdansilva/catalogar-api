package com.catalogar.catalogItemImage;

import com.catalogar.catalog.Catalog;
import com.catalogar.image.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Entity(name = "CatalogItemImage")
@EntityListeners(AuditingEntityListener.class)
public class CatalogItemImage extends Image {
    @Column(
            name = "position",
            columnDefinition = "SMALLINT",
            nullable = false
    )
    private Short position;

    public CatalogItemImage(
            String name,
            String url,
            Long size,
            Catalog catalog,
            Short width,
            Short height,
            String altText,
            Short position
    ) {
        super(name, url, size, catalog, width, height, altText);
        this.position = position;
    }

    public CatalogItemImage() {
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CatalogItemImage that = (CatalogItemImage) o;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position);
    }

    @Override
    public String toString() {
        return "CatalogItemImage{" +
                "position=" + position +
                "} " + super.toString();
    }
}
