package com.catalogar.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Category")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "category")
public class Category {

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
            name = "text_color",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String textColor;

    @Column(
            name = "background_color",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String backgroundColor;

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

    public Category() {
    }

    public Category(String name,
                    String slug,
                    String textColor,
                    String backgroundColor) {
        this.name = name;
        this.slug = slug;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public Category(UUID id,
                    String name,
                    String slug,
                    String textColor,
                    String backgroundColor,
                    LocalDateTime disabledAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.disabledAt = disabledAt;
    }

    public Category(UUID id,
                    String name,
                    String slug,
                    String textColor,
                    String backgroundColor,
                    LocalDateTime disabledAt,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.disabledAt = disabledAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(slug, category.slug) && Objects.equals(textColor, category.textColor) && Objects.equals(backgroundColor, category.backgroundColor) && Objects.equals(disabledAt, category.disabledAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slug, textColor, backgroundColor, disabledAt);
    }
}
