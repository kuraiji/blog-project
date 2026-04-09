package com.kuraiji.blog.domain.dto;

import com.kuraiji.blog.domain.entity.Post;
import com.kuraiji.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private UUID id;

    private Post post;

    private User owner;

    private String content;

    private Instant created;

    private Instant updated;
}
