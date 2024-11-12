package com.movie.repository;


import com.movie.entity.ActorsEntity;
import com.movie.entity.CountryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryRepository extends MongoRepository<CountryEntity, String> {
    boolean existsByName(String str);
}
