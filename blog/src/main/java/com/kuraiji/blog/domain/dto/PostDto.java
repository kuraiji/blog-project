package com.kuraiji.blog.domain.dto;

import com.kuraiji.blog.domain.entity.Comment;
import com.kuraiji.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private UUID id;

    private String title;

    private String body;

    private User owner;

    private Instant created;

    private Instant updated;

    private List<Comment> list;
}
