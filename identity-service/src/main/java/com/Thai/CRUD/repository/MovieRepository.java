package com.Thai.CRUD.repository;

import com.Thai.CRUD.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {
}
