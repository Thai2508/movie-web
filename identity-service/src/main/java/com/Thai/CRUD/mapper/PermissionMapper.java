package com.Thai.CRUD.mapper;

import com.Thai.CRUD.dto.request.PermissionRequest;
import com.Thai.CRUD.dto.response.PermissionResponse;
import com.Thai.CRUD.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(PermissionEntity permissionEntity);
}
