package com.movie.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Document("movie")
public class MovieEntity {
    @MongoId
    String movieId;
    String movieName;
    List<String> category;
    String releaseYear;
    String country;
    String duration;
    String quality;
    //    Poster, Video
    List<String> actors;
    String director;
    String description;
}
