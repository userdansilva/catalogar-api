package com.catalogar.productType;

import com.catalogar.catalog.Catalog;
import com.catalogar.catalogItem.CatalogItem;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "ProductType")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            nullable = false,
            columnDefinition = "UUID"
    )
    private UUID id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "slug",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String slug;

    @Column(
            name = "disabled_at",
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime disabledAt;

    @CreatedDate
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(
            name = "updated_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "catalog_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "product_type_catalog_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

    @OneToMany(
            mappedBy = "productType",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<CatalogItem> catalogItems = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDateTime getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(LocalDateTime disabledAt) {
        this.disabledAt = disabledAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(List<CatalogItem> catalogItems) {
        this.catalogItems = catalogItems;
    }

    public ProductType() {
    }

    public ProductType(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductType productType = (ProductType) o;
        return Objects.equals(id, productType.id) && Objects.equals(name, productType.name) && Objects.equals(slug, productType.slug) && Objects.equals(disabledAt, productType.disabledAt) && Objects.equals(createdAt, productType.createdAt) && Objects.equals(updatedAt, productType.updatedAt) && Objects.equals(catalog, productType.catalog) && Objects.equals(catalogItems, productType.catalogItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slug, disabledAt, createdAt, updatedAt, catalog, catalogItems);
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", disabledAt=" + disabledAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", catalog=" + catalog +
                ", catalogItems=" + catalogItems +
                '}';
    }
}
