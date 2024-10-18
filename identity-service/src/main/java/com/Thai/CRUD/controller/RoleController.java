package com.Thai.CRUD.controller;

import com.Thai.CRUD.dto.request.ApiResponse;
import com.Thai.CRUD.dto.request.AuthenticationRequest;
import com.Thai.CRUD.dto.request.IntrospectRequest;
import com.Thai.CRUD.dto.request.RoleRequest;
import com.Thai.CRUD.dto.response.AuthenticationResponse;
import com.Thai.CRUD.dto.response.IntrospectResponse;
import com.Thai.CRUD.dto.response.RoleResponse;
import com.Thai.CRUD.service.AuthenticationService;
import com.Thai.CRUD.service.RoleService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController {
    RoleService roleService;

    @PostMapping("create")
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping("getRoles")
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getRoles())
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<Objects> delete(@PathVariable String id) {
        roleService.delete(id);
        return ApiResponse.<Objects>builder()
                .message("Delete !!!")
                .build();
    }
}
