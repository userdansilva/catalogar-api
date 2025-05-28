package com.catalogar.image;

import com.catalogar.catalogItem.CatalogItem;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Image")
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "image",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "image_url_unique",
                        columnNames = "url"
                )
        }
)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id",
            updatable = false,
            columnDefinition = "UUID"
    )
    private UUID id;

    @Column(
            name = "file_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String fileName;

    @Column(
            name = "url",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String url;

    @Column(
            name = "position",
            columnDefinition = "SMALLINT",
            nullable = false
    )
    private Short position;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
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

    @CreatedDate
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime createdAt;



    public Image(String fileName, String url, Short position, CatalogItem catalogItem) {
        this.fileName = fileName;
        this.url = url;
        this.position = position;
        this.catalogItem = catalogItem;
    }

    public Image() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(fileName, image.fileName) && Objects.equals(url, image.url) && Objects.equals(position, image.position) && Objects.equals(createdAt, image.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, url, position, createdAt);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", position=" + position +
                ", createdAt=" + createdAt +
                '}';
    }
}
