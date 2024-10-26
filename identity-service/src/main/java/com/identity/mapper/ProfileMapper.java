package com.identity.mapper;

import com.identity.dto.request.ProfileRequest;
import com.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileRequest toProfileRequest(UserCreationRequest request);
}
