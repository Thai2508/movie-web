package com.movie.repository;


import com.movie.entity.ActorsEntity;
import com.movie.entity.DirectorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectorRepository extends MongoRepository<DirectorEntity, String> {
    boolean existsByName(String str);
}
