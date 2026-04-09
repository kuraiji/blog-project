package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.PostDto;
import com.kuraiji.blog.domain.entity.Post;
import com.kuraiji.blog.domain.entity.User;
import com.kuraiji.blog.domain.request.CreatePostRequest;
import com.kuraiji.blog.exception.UserNotFoundException;
import com.kuraiji.blog.repositories.PostRepository;
import com.kuraiji.blog.repositories.UserRepository;
import com.kuraiji.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final Mapper<Post, PostDto> postMapper;

    private final UserRepository userRepository;


    @Override
    public PostDto createPost(CreatePostRequest request, Long uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));
        Post post = Post.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .owner(user)
                .build();
        return postMapper.mapTo(postRepository.save(post));
    }

    @Override
    public List<PostDto> findAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .map(postMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PostDto> findOne(UUID id) {
        return postRepository.findById(id).map(postMapper::mapTo);
    }

    @Override
    public boolean notExists(UUID id) {
        return !postRepository.existsById(id);
    }

    @Override
    public PostDto fullUpdate(CreatePostRequest request, UUID id) {
        return postRepository.findById(id).map(existingPost -> {
            existingPost.setBody(request.getBody());
            existingPost.setTitle(request.getTitle());
            existingPost.setUpdated(Instant.now());
            return postMapper.mapTo(postRepository.save(existingPost));
        }).orElseThrow(() -> new RuntimeException("Post does not exist"));
    }

    @Override
    public PostDto partialUpdate(CreatePostRequest request, UUID id) {
        return postRepository.findById(id).map(existingPost -> {
            AtomicBoolean edited = new AtomicBoolean(false);
            Optional.ofNullable(request.getBody()).ifPresent(body -> {
                edited.set(true);
                existingPost.setBody(body);
            });
            Optional.ofNullable(request.getTitle()).ifPresent(title -> {
                edited.set(true);
                existingPost.setTitle(title);
            });
            if(edited.get()) {
                existingPost.setUpdated(Instant.now());
            }
            return postMapper.mapTo(postRepository.save(existingPost));
        }).orElseThrow(() -> new RuntimeException("Post does not exist"));
    }

    @Override
    public void delete(UUID id) {
        postRepository.deleteById(id);
    }
}
