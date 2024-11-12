package com.post.repository;

import com.post.entity.CommentEntity;
import com.post.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    List<UserEntity> findAllByNickName(String nickName);
}