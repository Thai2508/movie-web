package com.Thai.CRUD.mapper;

import com.Thai.CRUD.dto.request.MovieRequest;
import com.Thai.CRUD.dto.request.RoleRequest;
import com.Thai.CRUD.dto.response.MovieResponse;
import com.Thai.CRUD.dto.response.RoleResponse;
import com.Thai.CRUD.entity.MovieEntity;
import com.Thai.CRUD.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieEntity toMovieEntity(MovieRequest request);
    MovieResponse toMovieResponse(MovieEntity movieEntity);
}
