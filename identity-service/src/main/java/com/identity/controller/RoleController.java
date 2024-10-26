package com.identity.controller;

import com.identity.dto.request.ApiResponse;
import com.identity.dto.request.RoleRequest;
import com.identity.dto.response.RoleResponse;
import com.identity.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
