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
@Table(name = "application_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User(String username, String hashedPassword, String salt, String email, Role role) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.email = email;
        this.role = role;
    }
}
