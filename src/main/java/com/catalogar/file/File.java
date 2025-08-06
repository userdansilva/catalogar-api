package com.catalogar.file;

import com.catalogar.catalog.Catalog;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "File")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "file",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "file_url_unique",
                        columnNames = "url"
                )
        }
)
public class File {
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
            name = "size",
            columnDefinition = "BIGINT",
            nullable = false
    )
    private Long size;

    @CreatedDate
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "catalog_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "file_catalog_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

    public File(String name, String url, Long size, Catalog catalog) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.catalog = catalog;
    }

    public File() {
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(name, file.name) && Objects.equals(url, file.url) && Objects.equals(size, file.size) && Objects.equals(createdAt, file.createdAt) && Objects.equals(catalog, file.catalog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, size, createdAt, catalog);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", createdAt=" + createdAt +
                ", catalog=" + catalog +
                '}';
    }
}
