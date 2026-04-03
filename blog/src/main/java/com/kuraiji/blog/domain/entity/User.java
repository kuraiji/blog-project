package com.kuraiji.blog.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.kuraiji.blog.common.constants.ValidationConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "handle", unique = true, nullable = false, length = ValidationConstants.MAX_HANDLE_LENGTH)
    private String handle;

    @Column(name = "email", unique = true, nullable = false, length = ValidationConstants.MAX_EMAIL_LENGTH)
    private String email;

    @Column(name = "password_hash", nullable = false, length = ValidationConstants.MAX_PHASH_LENGTH)
    private String passwordHash;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;
}
