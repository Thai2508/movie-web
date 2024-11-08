package com.identity.controller;

import com.identity.dto.request.ApiResponse;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.request.UserUpdateRequest;
import com.identity.dto.response.UserResponse;
import com.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createUser(userCreationRequest));

        return apiResponse;
    }

    @PostMapping("/sendEmail")
    public ApiResponse<?> sendEmail() {
        userService.sendingEmail();
        return ApiResponse.builder()
                .message("Sent !!")
                .build();
    }

    @GetMapping("/getUsers")
    public ApiResponse<UserResponse> getListUsers() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.getUsers());

        return apiResponse;
    }

    @GetMapping("/myInfo")
//    @PostAuthorize("returnObject.name == authentication.name")
    public ApiResponse<UserResponse> getUser() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.getMyInfo());

        return apiResponse;
    }

    @PutMapping("/updateUser")
    public ApiResponse<UserResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.updateUser(userUpdateRequest));
        return apiResponse;
    }

    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
        return ApiResponse.builder()
                .message("DELETED !!!")
                .build();
    }

    @DeleteMapping("/deleteAll")
    public ApiResponse deleteAllUser() {
        userService.deleteAllUser();
        return ApiResponse.builder()
                .message("DELETED !!!")
                .build();
    }
}
