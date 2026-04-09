package com.kuraiji.blog.common.mappers.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.PostDto;
import com.kuraiji.blog.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapperImpl implements Mapper<Post, PostDto> {

    private final ModelMapper modelMapper;

    @Override
    public PostDto mapTo(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Post mapFrom(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
