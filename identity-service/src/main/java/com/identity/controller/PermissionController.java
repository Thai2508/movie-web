package com.identity.controller;

import com.identity.dto.request.ApiResponse;
import com.identity.dto.request.PermissionRequest;
import com.identity.dto.response.PermissionResponse;
import com.identity.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("permission")
public class PermissionController {
    PermissionService service;

    @PostMapping("create")
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(service.createPermission(request))
                .build();
    }

    @GetMapping("getPermissions")
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(service.getPermissions())
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<Object> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.builder()
                .message("Deleted !!!")
                .build();
    }
}
