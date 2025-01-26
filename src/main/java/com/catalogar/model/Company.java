package com.catalogar.model;

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

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "catalog_id",
            referencedColumnName = "id",
            updatable = false,
            nullable = false
    )
    private Catalog catalog;

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
            name = "site_url",
            columnDefinition = "TEXT"
    )
    private String siteUrl;

    @Column(
            name = "phone_number",
            columnDefinition = "TEXT"
    )
    private String phoneNumber;

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

    public Company() {
    }

    public Company(String name, String siteUrl, String phoneNumber, String logoUrl) {
        this.name = name;
        this.siteUrl = siteUrl;
        this.phoneNumber = phoneNumber;
        this.logoUrl = logoUrl;
    }

    public Company(String name, String siteUrl, String phoneNumber, String logoUrl, Catalog catalog) {
        this.name = name;
        this.siteUrl = siteUrl;
        this.phoneNumber = phoneNumber;
        this.logoUrl = logoUrl;
        this.catalog = catalog;
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

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(siteUrl, company.siteUrl) && Objects.equals(phoneNumber, company.phoneNumber) && Objects.equals(logoUrl, company.logoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, siteUrl, phoneNumber, logoUrl);
    }
}
