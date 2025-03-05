package com.catalogar.catalogItem;

import com.catalogar.catalog.Catalog;
import com.catalogar.category.Category;
import com.catalogar.image.Image;
import com.catalogar.product.Product;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "CatalogItem")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "catalog_item")
public class CatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "UUID"
    )
    private UUID id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name = "caption",
            columnDefinition = "TEXT"
    )
    private String caption;

    @Column(
            name = "price",
            precision = 10,
            scale = 2,
            columnDefinition = "NUMERIC(10, 2)"
    )
    private BigDecimal price;

    @Column(
            name = "reference",
            nullable = false,
            unique = true,
            columnDefinition = "INTEGER"
    )
    private Long reference;

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
                    name = "catalog_item_catalog_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "catalog_item_product_id_fk"
            ),
            nullable = false
    )
    private Product product;

    @ManyToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "catalog_item_category",
            joinColumns = @JoinColumn(
                    name = "catalog_item_id",
                    foreignKey = @ForeignKey(
                            name = "catalog_item_category_catalog_item_id_fk"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    foreignKey = @ForeignKey(
                            name = "catalog_item_category_category_id_fk"
                    )
            )
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(
            mappedBy = "catalogItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Image> images = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public CatalogItem(String title, String caption, BigDecimal price) {
        this.title = title;
        this.caption = caption;
        this.price = price;
    }

    public CatalogItem() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CatalogItem that = (CatalogItem) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(caption, that.caption) && Objects.equals(price, that.price) && Objects.equals(reference, that.reference) && Objects.equals(disabledAt, that.disabledAt) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(catalog, that.catalog) && Objects.equals(product, that.product) && Objects.equals(categories, that.categories) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, caption, price, reference, disabledAt, createdAt, updatedAt, catalog, product, categories, images);
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", caption='" + caption + '\'' +
                ", price=" + price +
                ", reference=" + reference +
                ", disabledAt=" + disabledAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", catalog=" + catalog +
                ", product=" + product +
                ", categories=" + categories +
                ", images=" + images +
                '}';
    }
}
