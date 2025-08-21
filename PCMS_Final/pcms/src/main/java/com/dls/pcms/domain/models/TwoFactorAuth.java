package com.dls.pcms.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "two_factor_auth")
public class   TwoFactorAuth {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "auth_id", updatable = false, nullable = false)
    private UUID authId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "method_type", nullable = false)
    private String methodType;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public TwoFactorAuth(UUID userId, String methodType, boolean isEnabled) {
        this.userId = userId;
        this.methodType = methodType;
        this.isEnabled = isEnabled;
    }
}
