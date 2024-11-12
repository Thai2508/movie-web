package com.movie.repository;


import com.movie.entity.ActorsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorsRepository extends MongoRepository<ActorsEntity, String> {
    boolean existsByName(String str);
}
