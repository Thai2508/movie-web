package com.movie.service;

import com.movie.dto.request.MovieRequest;
import com.movie.dto.response.CommonResponse;
import com.movie.dto.response.MovieResponse;
import com.movie.entity.*;
import com.movie.exception.AppException;
import com.movie.exception.ErrorCode;
import com.movie.mapper.CommonMapper;
import com.movie.mapper.MovieMapper;
import com.movie.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
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
    CategoryRepository categoryRepository;
    CommonMapper commonMapper;
    ActorsRepository actorsRepository;
    CountryRepository countryRepository;
    DirectorRepository directorRepository;
    YearRepository yearRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    public MovieResponse createMovie(MovieRequest request) {
        MovieEntity movie = movieMapper.toMovieEntity(request);
        movie.setDuration(request.getDuration()+" min");

        addCommon(request);
        movie = movieRepository.save(movie);
        List<String> list = List.of(movie.getMovieId(), movie.getMovieName());
        kafkaTemplate.send("getMovieName", list);

        return movieMapper.toMovieResponse(movie);
    }

    public void addCommon(MovieRequest request) {
        var listCategory = request.getCategory();
        var listActors = request.getActors();
        var country = request.getCountry();
        var director = request.getDirector();
        var year = request.getReleaseYear();

        for (String list: listCategory)
            if (!categoryRepository.existsByName(list))
                categoryRepository.save(CategoryEntity.builder().name(list).build());

        for (String list: listActors)
            if (!actorsRepository.existsByName(list))
                actorsRepository.save(ActorsEntity.builder().name(list).build());

        if (!countryRepository.existsByName(country))
            countryRepository.save(CountryEntity.builder().name(country).build());

        if (!directorRepository.existsByName(director))
            directorRepository.save(DirectorEntity.builder().name(director).build());

        if (!yearRepository.existsByName(year))
            yearRepository.save(ReleaseYearEntity.builder().name(year).build());

    }

    public MovieResponse getInfoMovie(String movieId) {
        var info = movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return movieMapper.toMovieResponse(info);
    }

    public List<MovieResponse> getAllMovie() {
        return movieRepository.findAll().stream().map(movieMapper::toMovieResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public List<CommonResponse> getAllCommon(String common) {
        return switch (common) {
            case "actor" -> actorsRepository.findAll().stream().map(commonMapper::actorsResponse).toList();
            case "country" -> countryRepository.findAll().stream().map(commonMapper::countryResponse).toList();
            case "director" -> directorRepository.findAll().stream().map(commonMapper::directorResponse).toList();
            case "year" -> yearRepository.findAll().stream().map(commonMapper::yearResponse).toList();
            case "category" -> categoryRepository.findAll().stream().map(commonMapper::categoryResponse).toList();
            default -> throw new AppException(ErrorCode.NOT_FOUND);
        };
    }

}
