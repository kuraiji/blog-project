package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.PostDto;
import com.kuraiji.blog.domain.request.CreatePostRequest;
import com.kuraiji.blog.exception.PostNotFoundException;
import com.kuraiji.blog.services.PostService;
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
@RequestMapping("/v1/posts")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(path = "")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequest request,
            @RequestAttribute("userId") Long userId
    ) {
        return new ResponseEntity<>(postService.createPost(request, userId), HttpStatus.CREATED);
    }

    @GetMapping(path = "")
    public List<PostDto> listPosts() {
        return postService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
        Optional<PostDto> foundPost = postService.findOne(id);
        return foundPost.map(ResponseEntity::ok)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> fullUpdatePost(
            @PathVariable UUID id,
            @Valid @RequestBody CreatePostRequest request
    ) {
        if(postService.notExists(id)) {
            throw new PostNotFoundException(id);
        }
        return new ResponseEntity<>(postService.fullUpdate(request, id), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PostDto> partialUpdatePost(
            @PathVariable UUID id,
            @Valid @RequestBody CreatePostRequest request
    ) {
        if(postService.notExists(id)) {
            throw new PostNotFoundException(id);
        }
        return new ResponseEntity<>(postService.partialUpdate(request, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable UUID id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
