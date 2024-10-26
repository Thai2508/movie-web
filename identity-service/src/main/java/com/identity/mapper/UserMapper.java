package com.identity.mapper;

import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.request.UserUpdateRequest;
import com.identity.dto.response.UserResponse;
import com.identity.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    UserEntity toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(UserEntity userEntity);
    @Mapping(target = "roles", ignore = true)
    void userUpdate(@MappingTarget UserEntity user, UserUpdateRequest updateRequest);

}
