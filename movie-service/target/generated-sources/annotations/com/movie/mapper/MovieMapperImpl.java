package com.movie.mapper;

import com.movie.dto.request.MovieRequest;
import com.movie.dto.response.MovieResponse;
import com.movie.entity.MovieEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-12T10:36:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public MovieEntity toMovieEntity(MovieRequest request) {
        if ( request == null ) {
            return null;
        }

        MovieEntity.MovieEntityBuilder movieEntity = MovieEntity.builder();

        movieEntity.movieName( request.getMovieName() );
        List<String> list = request.getCategory();
        if ( list != null ) {
            movieEntity.category( new ArrayList<String>( list ) );
        }
        movieEntity.releaseYear( request.getReleaseYear() );
        movieEntity.country( request.getCountry() );
        movieEntity.duration( request.getDuration() );
        movieEntity.quality( request.getQuality() );
        List<String> list1 = request.getActors();
        if ( list1 != null ) {
            movieEntity.actors( new ArrayList<String>( list1 ) );
        }
        movieEntity.director( request.getDirector() );
        movieEntity.description( request.getDescription() );

        return movieEntity.build();
    }

    @Override
    public MovieResponse toMovieResponse(MovieEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MovieResponse.MovieResponseBuilder movieResponse = MovieResponse.builder();

        movieResponse.movieId( entity.getMovieId() );
        movieResponse.movieName( entity.getMovieName() );
        List<String> list = entity.getCategory();
        if ( list != null ) {
            movieResponse.category( new ArrayList<String>( list ) );
        }
        movieResponse.releaseYear( entity.getReleaseYear() );
        movieResponse.country( entity.getCountry() );
        movieResponse.duration( entity.getDuration() );
        movieResponse.quality( entity.getQuality() );
        List<String> list1 = entity.getActors();
        if ( list1 != null ) {
            movieResponse.actors( new ArrayList<String>( list1 ) );
        }
        movieResponse.director( entity.getDirector() );
        movieResponse.description( entity.getDescription() );

        return movieResponse.build();
    }
}
