package com.movie.service;

import com.movie.dto.request.MovieRequest;
import com.movie.dto.response.MovieResponse;
import com.movie.exception.AppException;
import com.movie.exception.ErrorCode;
import com.movie.mapper.MovieMapper;
import com.movie.repository.MovieRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    MovieMapper movieMapper;
    MovieRepository movieRepository;

    public MovieResponse createMovie(MovieRequest request) {
        var movie = movieMapper.toMovieEntity(request);
        movie.setDuration(request.getDuration()+" min");
        log.info(movie.getMovieId());
        return movieMapper.toMovieResponse(movieRepository.save(movie));
    }

    public MovieResponse getInfoMovie(String movieId) {
        var info = movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_FOUND));

        return movieMapper.toMovieResponse(info);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<MovieResponse> getAllMovie() {
        return movieRepository.findAll().stream().map(movieMapper::toMovieResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() {
        movieRepository.deleteAll();
    }

}
