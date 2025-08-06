package com.catalogar.catalogItemImage;

import com.catalogar.catalog.Catalog;
import com.catalogar.catalogItem.CatalogItem;
import com.catalogar.image.Image;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "catalog_item_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "image_catalog_item_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private CatalogItem catalogItem;

    public CatalogItemImage(
            String name,
            String url,
            Long size,
            Catalog catalog,
            Short width,
            Short height,
            String altText,
            Short position,
            CatalogItem catalogItem
    ) {
        super(name, url, size, catalog, width, height, altText);
        this.position = position;
        this.catalogItem = catalogItem;
    }

    public CatalogItemImage() {
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public CatalogItem getCatalogItem() {
        return catalogItem;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CatalogItemImage that = (CatalogItemImage) o;
        return Objects.equals(position, that.position) && Objects.equals(catalogItem, that.catalogItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), position, catalogItem);
    }

    @Override
    public String toString() {
        return "CatalogItemImage{" +
                "position=" + position +
                ", catalogItem=" + catalogItem +
                "} " + super.toString();
    }
}
