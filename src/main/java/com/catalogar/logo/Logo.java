package com.catalogar.logo;

import com.catalogar.theme.Theme;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Logo")
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "logo",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "logo_url_unique",
                        columnNames = "url"
                )
        }
)
public class Logo {
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
            name = "url",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String url;

    @Column(
            name = "width",
            columnDefinition = "SMALLINT",
            nullable = false
    )
    private Short width;

    @Column(
            name = "height",
            columnDefinition = "SMALLINT",
            nullable = false
    )
    private Short height;

    @CreatedDate
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime createdAt;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "theme_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "logo_theme_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Theme theme;

    public Logo() {
    }

    public Logo(String name, Short width, Short height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public Logo(String name, String url, Short width, Short height, Theme theme) {
        this.name = name;
        this.url = url;
        this.width = width;
        this.height = height;
        this.theme = theme;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Short getWidth() {
        return width;
    }

    public void setWidth(Short width) {
        this.width = width;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Logo logo = (Logo) o;
        return Objects.equals(id, logo.id) && Objects.equals(name, logo.name) && Objects.equals(url, logo.url) && Objects.equals(width, logo.width) && Objects.equals(height, logo.height) && Objects.equals(createdAt, logo.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, width, height, createdAt);
    }

    @Override
    public String toString() {
        return "Logo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", createdAt=" + createdAt +
                '}';
    }
}
