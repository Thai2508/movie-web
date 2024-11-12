package com.movie.repository;


import com.movie.entity.ActorsEntity;
import com.movie.entity.ReleaseYearEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YearRepository extends MongoRepository<ReleaseYearEntity, String> {
    boolean existsByName(String str);
}
