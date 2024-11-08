package com.movie.repository;

import com.movie.entity.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface MovieRepository extends MongoRepository<MovieEntity, String> {
}
