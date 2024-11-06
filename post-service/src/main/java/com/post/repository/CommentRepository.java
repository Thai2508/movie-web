package com.post.repository;

import com.post.entity.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findAllByMovieId(String movieId);
}
