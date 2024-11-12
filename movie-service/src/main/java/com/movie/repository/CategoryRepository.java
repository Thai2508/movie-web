package com.movie.repository;


import com.movie.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {
    boolean existsByName(String str);
}
