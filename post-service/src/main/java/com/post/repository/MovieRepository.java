package com.post.repository;

import com.post.dto.response.CommentResponse;
import com.post.entity.MovieEntity;
import com.post.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity, String> {
    List<MovieEntity> findAllByMovieName(String movieName);
    List<CommentResponse> findAllById(String id);
}