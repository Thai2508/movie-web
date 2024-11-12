package com.search.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class KeyWordRequest {
    String searchBox;
    String category;
    String releaseYear;
    String country;
}
