package com.catalogar.company;

import com.catalogar.catalog.Catalog;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Company")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company")
public class Company {

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
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "main_site_url",
            columnDefinition = "TEXT"
    )
    private String mainSiteUrl;

    @Column(
            name = "phone_number",
            columnDefinition = "TEXT"
    )
    private String phoneNumber;

    @Column(
            name = "business_type_description",
            columnDefinition = "TEXT"
    )
    private String businessTypeDescription;

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

    @OneToOne
    @JoinColumn(
            name = "catalog_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "company_catalog_id_fk"
            ),
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

    public Company(String name, String description, String mainSiteUrl, String phoneNumber, String businessTypeDescription) {
        this.name = name;
        this.description = description;
        this.mainSiteUrl = mainSiteUrl;
        this.phoneNumber = phoneNumber;
        this.businessTypeDescription = businessTypeDescription;
    }

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainSiteUrl() {
        return mainSiteUrl;
    }

    public void setMainSiteUrl(String mainSiteUrl) {
        this.mainSiteUrl = mainSiteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusinessTypeDescription() {
        return businessTypeDescription;
    }

    public void setBusinessTypeDescription(String businessTypeDescription) {
        this.businessTypeDescription = businessTypeDescription;
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
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(description, company.description) && Objects.equals(mainSiteUrl, company.mainSiteUrl) && Objects.equals(phoneNumber, company.phoneNumber) && Objects.equals(businessTypeDescription, company.businessTypeDescription) && Objects.equals(createdAt, company.createdAt) && Objects.equals(updatedAt, company.updatedAt) && Objects.equals(catalog, company.catalog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, mainSiteUrl, phoneNumber, businessTypeDescription, createdAt, updatedAt, catalog);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mainSiteUrl='" + mainSiteUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", businessTypeDescription='" + businessTypeDescription + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", catalog=" + catalog +
                '}';
    }
}
