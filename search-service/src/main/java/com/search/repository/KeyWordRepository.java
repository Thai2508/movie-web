package com.search.repository;

import com.search.entity.KeyWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyWordRepository extends MongoRepository<KeyWord, String> {
}
