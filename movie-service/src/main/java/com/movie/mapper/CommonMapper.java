package com.movie.mapper;

import com.movie.dto.response.CommonResponse;
import com.movie.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommonMapper {
    CommonResponse categoryResponse(CategoryEntity entity);
    CommonResponse actorsResponse(ActorsEntity entity);
    CommonResponse countryResponse(CountryEntity entity);
    CommonResponse directorResponse(DirectorEntity entity);
    CommonResponse yearResponse(ReleaseYearEntity entity);
}
