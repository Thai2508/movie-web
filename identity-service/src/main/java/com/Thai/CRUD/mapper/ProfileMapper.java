package com.Thai.CRUD.mapper;

import com.Thai.CRUD.dto.request.ProfileRequest;
import com.Thai.CRUD.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileRequest toProfileRequest(UserCreationRequest request);
}
