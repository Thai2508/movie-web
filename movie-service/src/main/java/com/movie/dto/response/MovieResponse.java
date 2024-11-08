package com.movie.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
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
