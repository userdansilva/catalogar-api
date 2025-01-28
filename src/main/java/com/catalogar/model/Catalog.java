package com.catalogar.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
            name = "slug",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String slug;

    @Column(
            name = "published_at",
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private ZonedDateTime publishedAt;

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
            orphanRemoval = true
    )
    private Company company;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ZonedDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(ZonedDateTime publishedAt) {
        this.publishedAt = publishedAt;
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

    public Catalog() {
    }

    public Catalog(String slug) {
        this.slug = slug;
    }

    public Catalog(String slug, ZonedDateTime publishedAt) {
        this.slug = slug;
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id) && Objects.equals(slug, catalog.slug) && Objects.equals(publishedAt, catalog.publishedAt) && Objects.equals(createdAt, catalog.createdAt) && Objects.equals(updatedAt, catalog.updatedAt) && Objects.equals(company, catalog.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slug, publishedAt, createdAt, updatedAt, company);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", company=" + company +
                '}';
    }
}
