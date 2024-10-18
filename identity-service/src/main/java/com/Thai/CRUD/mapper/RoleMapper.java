package com.Thai.CRUD.mapper;

import com.Thai.CRUD.dto.request.RoleRequest;
import com.Thai.CRUD.dto.response.RoleResponse;
import com.Thai.CRUD.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    RoleEntity toRole(RoleRequest request);
    RoleResponse toRoleResponse(RoleEntity roleEntity);
}
