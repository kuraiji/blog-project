package com.kuraiji.blog.common.mappers.impl;

import com.kuraiji.blog.common.mappers.Mapper;
import com.kuraiji.blog.domain.dto.CommentDto;
import com.kuraiji.blog.domain.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapperImpl implements Mapper<Comment, CommentDto> {

    private final ModelMapper modelMapper;

    @Override
    public CommentDto mapTo(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public Comment mapFrom(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
