package com.profile.mapper;

import com.profile.dto.request.ProfileRequest;
import com.profile.dto.response.ProfileResponse;
import com.profile.entity.ProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileEntity toProfileEntity(ProfileRequest request);
    ProfileResponse toProfileResponse(ProfileEntity entity);
}
