package com.Thai.CRUD.controller;

import com.Thai.CRUD.dto.request.ApiResponse;
import com.Thai.CRUD.dto.request.AuthenticationRequest;
import com.Thai.CRUD.dto.request.IntrospectRequest;
import com.Thai.CRUD.dto.request.PermissionRequest;
import com.Thai.CRUD.dto.response.AuthenticationResponse;
import com.Thai.CRUD.dto.response.IntrospectResponse;
import com.Thai.CRUD.dto.response.PermissionResponse;
import com.Thai.CRUD.service.AuthenticationService;
import com.Thai.CRUD.service.PermissionService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
