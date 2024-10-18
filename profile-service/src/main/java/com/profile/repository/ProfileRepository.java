package com.profile.repository;

import com.profile.entity.ProfileEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends Neo4jRepository<ProfileEntity, String> {


}
