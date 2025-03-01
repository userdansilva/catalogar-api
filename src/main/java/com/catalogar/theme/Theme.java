package com.catalogar.theme;

import com.catalogar.catalog.Catalog;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Theme")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "UUID"
    )
    private UUID id;

    @Column(
            name = "primary_color",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String primaryColor;

    @Column(
            name = "secondary_color",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String secondaryColor;

    @Column(
            name = "logo_url",
            columnDefinition = "TEXT"
    )
    private String logoUrl;

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
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "catalog_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "theme_catalog_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

    public Theme() {
    }

    public Theme(String primaryColor, String secondaryColor, String logoUrl) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.logoUrl = logoUrl;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return Objects.equals(id, theme.id) && Objects.equals(primaryColor, theme.primaryColor) && Objects.equals(secondaryColor, theme.secondaryColor) && Objects.equals(logoUrl, theme.logoUrl) && Objects.equals(createdAt, theme.createdAt) && Objects.equals(updatedAt, theme.updatedAt) && Objects.equals(catalog, theme.catalog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, primaryColor, secondaryColor, logoUrl, createdAt, updatedAt, catalog);
    }
}
