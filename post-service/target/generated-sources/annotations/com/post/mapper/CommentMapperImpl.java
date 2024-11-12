package com.post.mapper;

import com.post.dto.request.CommentRequest;
import com.post.dto.response.CommentResponse;
import com.post.dto.response.CommentResponseUser;
import com.post.entity.CommentEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-12T13:23:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentEntity toCommentEntity(CommentRequest request) {
        if ( request == null ) {
            return null;
        }

        CommentEntity.CommentEntityBuilder commentEntity = CommentEntity.builder();

        commentEntity.comment( request.getComment() );

        return commentEntity.build();
    }

    @Override
    public CommentResponse toCommentResponse(CommentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.nickName( entity.getNickName() );
        commentResponse.comment( entity.getComment() );
        commentResponse.dateComment( entity.getDateComment() );
        commentResponse.isModifier( entity.getIsModifier() );

        return commentResponse.build();
    }

    @Override
    public CommentResponseUser toCommentResponseUser(CommentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CommentResponseUser.CommentResponseUserBuilder commentResponseUser = CommentResponseUser.builder();

        commentResponseUser.movieName( entity.getMovieName() );
        commentResponseUser.comment( entity.getComment() );
        commentResponseUser.dateComment( entity.getDateComment() );
        commentResponseUser.isModifier( entity.getIsModifier() );

        return commentResponseUser.build();
    }
}
