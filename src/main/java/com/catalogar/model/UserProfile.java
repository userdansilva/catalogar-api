package com.catalogar.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "UserProfile")
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "user_profile",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_unique",
                        columnNames = "email"
                )
        }
)
public class UserProfile {

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
            name = "email",
            updatable = false,
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "phone_number",
            columnDefinition = "TEXT"
    )
    private String phoneNumber;

    @Column(
            name = "disable_reason",
            columnDefinition = "TEXT"
    )
    private String disableReason;

    @Column(
            name = "disabled_at",
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private ZonedDateTime disabledAt;

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

    public UserProfile() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile userProfile = (UserProfile) o;
        return Objects.equals(id, userProfile.id) && Objects.equals(name, userProfile.name) && Objects.equals(email, userProfile.email) && Objects.equals(phoneNumber, userProfile.phoneNumber) && Objects.equals(disableReason, userProfile.disableReason) && Objects.equals(disabledAt, userProfile.disabledAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phoneNumber, disableReason, disabledAt);
    }
}
