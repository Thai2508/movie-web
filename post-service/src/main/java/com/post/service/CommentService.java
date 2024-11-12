package com.post.service;

import com.post.controller.EventKafkaListener;
import com.post.dto.request.CommentRequest;
import com.post.dto.response.CommentResponse;
import com.post.dto.response.CommentResponseUser;
import com.post.entity.CommentEntity;
import com.post.entity.MovieEntity;
import com.post.entity.UserEntity;
import com.post.exception.AppException;
import com.post.exception.ErrorCode;
import com.post.mapper.CommentMapper;
import com.post.repository.CommentRepository;
import com.post.repository.MovieRepository;
import com.post.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public class CommentService {
    CommentRepository commentRepository;
    CommentMapper commentMapper;
    KafkaTemplate<String, Object> kafkaTemplate;
    UserRepository userRepository;
    MovieRepository movieRepository;

    public CommentResponse createComment(CommentRequest request, String movieId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        var comment = commentMapper.toCommentEntity(request);

        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));

        comment.setNickName(user.getNickName());
        comment.setMovieName(movie.getMovieName());
        comment.setNowComment(Instant.now());

        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    private String durationComment(Instant commentTime) {
        Instant present = Instant.now();
        Duration duration = Duration.between(commentTime, present);
        if (duration.getSeconds() < 60) {
            return duration.getSeconds() + " seconds ago";
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " minutes ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hours ago";
        } else if (duration.toDays() < 30) {
            return duration.toDays() + " days ago";
        } else if (duration.toDays() < 365) {
            return duration.toDays() / 30 + " months ago";
        } else {
            return duration.toDays() / 365 + " years ago";
        }
    }

    public List<CommentResponse> getCommentMovie(String movieId) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        for (CommentEntity c: commentRepository.findAllByMovieName(movie.getMovieName())) {
            c.setDateComment(durationComment(c.getNowComment()));
            commentRepository.save(c);
        }

        return commentRepository.findAllByMovieName(movie.getMovieName())
                                .stream().map(commentMapper::toCommentResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CommentResponseUser> getCommentUser(String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.NOT_FOUND));
        for (CommentEntity c: commentRepository.findAllByNickName(user.getNickName())) {
            c.setDateComment(durationComment(c.getNowComment()));
            commentRepository.save(c);
        }

        return commentRepository.findAllByNickName(user.getNickName())
                                .stream().map(commentMapper::toCommentResponseUser).toList();
    }

    public void deleteAll(){
        commentRepository.deleteAll();
        userRepository.deleteAll();
    }

}
