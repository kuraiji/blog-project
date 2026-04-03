package com.kuraiji.blog.domain.entity;

import com.kuraiji.blog.common.constants.ValidationConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments",
        indexes = {@Index(name = "idx_comments_post_id", columnList = "post_id")})
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "UUID DEFAULT uuidv7()", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content", nullable = false, length = ValidationConstants.MAX_CONTENT_LENGTH)
    private String content;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false, updatable = false)
    private Instant created;

    @ColumnDefault("now()")
    @Column(name = "updated", nullable = false)
    private Instant updated;
}
