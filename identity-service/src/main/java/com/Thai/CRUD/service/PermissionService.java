package com.Thai.CRUD.service;

import com.Thai.CRUD.dto.request.PermissionRequest;
import com.Thai.CRUD.dto.response.PermissionResponse;
import com.Thai.CRUD.exception.AppException;
import com.Thai.CRUD.exception.ErrorCode;
import com.Thai.CRUD.mapper.PermissionMapper;
import com.Thai.CRUD.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper mapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        var permission = mapper.toPermission(request);

        return mapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll().stream().map(mapper::toPermissionResponse).toList();
    }

    public void delete(String id) {
        if (!permissionRepository.existsById(id))
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        permissionRepository.deleteById(id);
    }
}
