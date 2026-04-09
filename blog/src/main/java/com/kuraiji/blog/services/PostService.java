package com.kuraiji.blog.services;

import com.kuraiji.blog.domain.dto.PostDto;
import com.kuraiji.blog.domain.request.CreatePostRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {
    PostDto createPost(CreatePostRequest request, Long uid);
    List<PostDto> findAll();
    Optional<PostDto> findOne(UUID id);
    boolean notExists(UUID id);
    PostDto fullUpdate(CreatePostRequest request, UUID id);
    PostDto partialUpdate(CreatePostRequest request, UUID id);
    void delete(UUID id);
}
