package com.Thai.CRUD.service;

import com.Thai.CRUD.dto.request.MovieRequest;
import com.Thai.CRUD.dto.response.MovieResponse;
import com.Thai.CRUD.entity.MovieEntity;
import com.Thai.CRUD.exception.AppException;
import com.Thai.CRUD.exception.ErrorCode;
import com.Thai.CRUD.mapper.MovieMapper;
import com.Thai.CRUD.repository.MovieRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MovieService {
    MovieRepository movieRepository;
    MovieMapper movieMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public MovieResponse addMovie(MovieRequest movieRequest) {
        MovieEntity movie = movieMapper.toMovieEntity(movieRequest);

        return movieMapper.toMovieResponse(movieRepository.save(movie));
    }

    public List<MovieResponse> getMovies() {
        return movieRepository.findAll().stream().map(movieMapper::toMovieResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(String id) {
        if (movieRepository.findById(id).isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else {
            movieRepository.deleteById(id);
        }
    }

}
