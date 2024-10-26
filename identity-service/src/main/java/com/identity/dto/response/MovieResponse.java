package com.identity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    String id;
    String name;
    String poster;
    String country;
    String releaseDate;
    String actors;
    String description;
    List<String> genres;
}
