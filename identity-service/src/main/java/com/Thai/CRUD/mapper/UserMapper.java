package com.Thai.CRUD.mapper;

import com.Thai.CRUD.dto.request.UserCreationRequest;
import com.Thai.CRUD.dto.request.UserUpdateRequest;
import com.Thai.CRUD.dto.response.UserResponse;
import com.Thai.CRUD.entity.UserEntity;
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
