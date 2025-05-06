package com.catalogar.catalog;

import com.catalogar.catalogItem.CatalogItem;
import com.catalogar.category.Category;
import com.catalogar.company.Company;
import com.catalogar.product.Product;
import com.catalogar.theme.Theme;
import com.catalogar.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Catalog")
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "catalog",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "catalog_slug_unique",
                        columnNames = "slug"
                )
        }
)
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            updatable = false,
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
            name = "published_at",
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime publishedAt;

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

    @OneToOne(
            mappedBy = "catalog",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Company company;

    @OneToOne(
            mappedBy = "catalog",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Theme theme;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "catalog_user_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private User user;

    @OneToMany(
            mappedBy = "catalog",
            orphanRemoval = true
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(
            mappedBy = "catalog",
            orphanRemoval = true
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(
            mappedBy = "catalog",
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

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isPublished() {
        return publishedAt != null;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean hasCompany() {
        return company != null;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public boolean hasTheme() {
        return theme != null;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(List<CatalogItem> catalogItems) {
        this.catalogItems = catalogItems;
    }

    public Catalog(User user, String name, String slug, LocalDateTime publishedAt, Company company) {
        this.user = user;
        this.name = name;
        this.slug = slug;
        this.publishedAt = publishedAt;
        this.company = company;
    }

    public Catalog(User user, String name, String slug, LocalDateTime publishedAt) {
        this(user, name, slug, publishedAt, null);
    }

    public Catalog(String name, String slug, LocalDateTime publishedAt) {
        this(null, name, slug, publishedAt);
    }

    public Catalog(String name, String slug) {
        this(name, slug, null);
    }

    public Catalog() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id) && Objects.equals(name, catalog.name) && Objects.equals(slug, catalog.slug) && Objects.equals(publishedAt, catalog.publishedAt) && Objects.equals(createdAt, catalog.createdAt) && Objects.equals(updatedAt, catalog.updatedAt) && Objects.equals(company, catalog.company) && Objects.equals(theme, catalog.theme) && Objects.equals(products, catalog.products) && Objects.equals(categories, catalog.categories) && Objects.equals(catalogItems, catalog.catalogItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slug, publishedAt, createdAt, updatedAt, company, theme, products, categories, catalogItems);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", company=" + company +
                ", theme=" + theme +
                ", products=" + products +
                ", categories=" + categories +
                ", catalogItems=" + catalogItems +
                '}';
    }
}
