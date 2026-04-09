package com.kuraiji.blog.services.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.CommentDto;
import com.kuraiji.blog.domain.entity.Comment;
import com.kuraiji.blog.domain.entity.Post;
import com.kuraiji.blog.domain.entity.User;
import com.kuraiji.blog.domain.request.CreateCommentRequest;
import com.kuraiji.blog.exception.PostNotFoundException;
import com.kuraiji.blog.exception.UserNotFoundException;
import com.kuraiji.blog.repositories.CommentRepository;
import com.kuraiji.blog.repositories.PostRepository;
import com.kuraiji.blog.repositories.UserRepository;
import com.kuraiji.blog.services.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final Mapper<Comment, CommentDto> commentMapper;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Override
    public CommentDto create(CreateCommentRequest request, Long uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new UserNotFoundException(uid));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new PostNotFoundException(request.getPostId()));
        Comment comment = Comment.builder()
                .content(request.getContent())
                .owner(user)
                .post(post)
                .build();
        return commentMapper.mapTo(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> findAll() {
        return StreamSupport.stream(commentRepository.findAll().spliterator(), false)
                .map(commentMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDto> findOne(UUID id) {
        return commentRepository.findById(id).map(commentMapper::mapTo);
    }

    @Override
    public boolean notExists(UUID id) {
        return !commentRepository.existsById(id);
    }

    @Override
    public CommentDto fullUpdate(CreateCommentRequest request, UUID id) {
        return commentRepository.findById(id).map(exisitingComment -> {
            exisitingComment.setContent(request.getContent());
            exisitingComment.setUpdated(Instant.now());
            return commentMapper.mapTo(commentRepository.save(exisitingComment));
        }).orElseThrow(() -> new RuntimeException("Comment does not exist"));
    }

    @Override
    public CommentDto partialUpdate(CreateCommentRequest request, UUID id) {
        return commentRepository.findById(id).map(exisitingComment -> {
            AtomicBoolean edited = new AtomicBoolean(false);
            Optional.ofNullable(request.getContent()).ifPresent(content -> {
                edited.set(true);
                exisitingComment.setContent(content);
            });
            if(edited.get()) {
                exisitingComment.setUpdated(Instant.now());
            }
            return commentMapper.mapTo(commentRepository.save(exisitingComment));
        }).orElseThrow(() -> new RuntimeException("Comment does not exist"));
    }

    @Override
    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }
}
