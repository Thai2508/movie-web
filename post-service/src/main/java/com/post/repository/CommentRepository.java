package com.post.repository;

import com.post.dto.response.CommentResponse;
import com.post.entity.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
    List<CommentEntity> findAllByNickName(String userName);
    List<CommentEntity> findAllByMovieName(String movieName);
}