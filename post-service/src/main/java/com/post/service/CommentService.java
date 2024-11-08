package com.post.service;

import com.post.dto.request.CommentRequest;
import com.post.dto.response.CommentResponse;
import com.post.entity.CommentEntity;
import com.post.exception.AppException;
import com.post.exception.ErrorCode;
import com.post.mapper.CommentMapper;
import com.post.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {
    CommentRepository commentRepository;
    CommentMapper commentMapper;

    public CommentResponse createComment(CommentRequest request, String movieId) {
        Instant datePresent = Instant.now();

        var comment = commentMapper.toCommentEntity(request);
        comment.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setMovieId(movieId);
        comment.setDateComment(datePresent);

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public List<CommentResponse> getCommentMovie(String id) {
        if (!commentRepository.findAllByUserId(id).isEmpty())
            return commentRepository.findAllByUserId(id).stream().map(commentMapper::toCommentResponse).toList();

        return commentRepository.findAllByMovieId(id).stream().map(commentMapper::toCommentResponse).toList();
    }

    public void deleteAll(){
        commentRepository.deleteAll();
    }

}
