package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.CommentDto;
import com.kuraiji.blog.domain.request.CreateCommentRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    CommentDto create(CreateCommentRequest request, Long uid);
    List<CommentDto> findAll();
    Optional<CommentDto> findOne(UUID id);
    boolean notExists(UUID id);
    CommentDto fullUpdate(CreateCommentRequest request, UUID id);
    CommentDto partialUpdate(CreateCommentRequest request, UUID id);
    void delete(UUID id);
}
