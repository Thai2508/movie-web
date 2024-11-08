package com.movie.mapper;

import com.movie.dto.request.MovieRequest;
import com.movie.dto.response.MovieResponse;
import com.movie.entity.MovieEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieEntity toMovieEntity(MovieRequest request);
    MovieResponse toMovieResponse(MovieEntity entity);
}
