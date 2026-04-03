package com.kuraiji.blog.domain.entity;

import com.kuraiji.blog.common.constants.ValidationConstants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "UUID DEFAULT uuidv7()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = ValidationConstants.MAX_TITLE_LENGTH)
    private String title;

    @Column(name = "body", nullable = false, length = ValidationConstants.MAX_BODY_LENGTH)
    private String body;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @ColumnDefault("now()")
    @Column(name = "updated", nullable = false)
    private Instant updated;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> list = new ArrayList<>();
}
