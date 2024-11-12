package com.post.mapper;

import com.post.dto.request.CommentRequest;
import com.post.dto.response.CommentResponse;
import com.post.dto.response.CommentResponseUser;
import com.post.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentEntity toCommentEntity(CommentRequest request);
    CommentResponse toCommentResponse(CommentEntity entity);
    CommentResponseUser toCommentResponseUser(CommentEntity entity);
}
