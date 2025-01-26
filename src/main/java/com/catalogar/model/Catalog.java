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

    public Catalog() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id) && Objects.equals(slug, catalog.slug) && Objects.equals(publishedAt, catalog.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, slug, publishedAt);
    }
}
