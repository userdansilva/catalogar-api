package com.catalogar.product;

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

@Entity(name = "Product")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product")
public class Product {

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
                    name = "product_catalog_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

    @OneToMany(
            mappedBy = "product",
            orphanRemoval = true
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

    public Product() {
    }

    public Product(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(slug, product.slug) && Objects.equals(disabledAt, product.disabledAt) && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt) && Objects.equals(catalog, product.catalog) && Objects.equals(catalogItems, product.catalogItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slug, disabledAt, createdAt, updatedAt, catalog, catalogItems);
    }

    @Override
    public String toString() {
        return "Product{" +
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
