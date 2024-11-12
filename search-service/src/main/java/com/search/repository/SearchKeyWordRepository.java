package com.search.repository;

import com.search.entity.KeyWord;
import com.search.entity.SearchKeyWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchKeyWordRepository extends MongoRepository<SearchKeyWord, String> {
    List<SearchKeyWord> findAllByUserId(String userId);
}
