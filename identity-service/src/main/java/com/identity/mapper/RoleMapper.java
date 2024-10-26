package com.identity.mapper;

import com.identity.dto.request.RoleRequest;
import com.identity.dto.response.RoleResponse;
import com.identity.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    RoleEntity toRole(RoleRequest request);
    RoleResponse toRoleResponse(RoleEntity roleEntity);
}
