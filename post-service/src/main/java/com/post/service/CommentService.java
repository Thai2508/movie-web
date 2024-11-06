package com.post.service;

import com.post.dto.request.CommentRequest;
import com.post.dto.response.CommentResponse;
import com.post.mapper.CommentMapper;
import com.post.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public CommentResponse createComment(CommentRequest request) {
        var comment = commentMapper.toCommentEntity(request);
        Instant datePresent = Instant.now();

        comment.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setDateComment(datePresent);

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public List<CommentResponse> getCommentMovie(String movieId) {
        return commentRepository.findAllByMovieId(movieId).stream().map(commentMapper::toCommentResponse).toList();
    }

}
