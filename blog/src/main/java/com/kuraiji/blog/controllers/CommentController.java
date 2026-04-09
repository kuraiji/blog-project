package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.CommentDto;
import com.kuraiji.blog.domain.request.CreateCommentRequest;
import com.kuraiji.blog.exception.CommentNotFoundException;
import com.kuraiji.blog.exception.PostNotFoundException;
import com.kuraiji.blog.services.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/comments")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "")
    public ResponseEntity<CommentDto> createComment(
            @Valid @RequestBody CreateCommentRequest request,
            @RequestAttribute("userId") Long userId
        ) {
        return new ResponseEntity<>(commentService.create(request, userId), HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<CommentDto> listComments() {
        return commentService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable UUID id) {
        Optional<CommentDto> foundComment = commentService.findOne(id);
        return foundComment.map(ResponseEntity::ok)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommentDto> fullUpdateComment(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCommentRequest request
        ) {
        if(commentService.notExists(id)) {
            throw new PostNotFoundException(id);
        }
        return ResponseEntity.ok(commentService.fullUpdate(request, id));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CommentDto> partialUpdateComment(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        if(commentService.notExists(id)) {
            throw new PostNotFoundException(id);
        }
        return ResponseEntity.ok(commentService.partialUpdate(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable UUID id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
