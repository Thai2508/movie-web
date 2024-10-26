package com.identity.mapper;

import com.identity.dto.request.MovieRequest;
import com.identity.dto.response.MovieResponse;
import com.identity.entity.MovieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieEntity toMovieEntity(MovieRequest request);
    MovieResponse toMovieResponse(MovieEntity movieEntity);
}
