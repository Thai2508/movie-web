package com.profile.controller;

import com.profile.dto.ApiResponse;
import com.profile.dto.request.ProfileRequest;
import com.profile.dto.response.ProfileResponse;
import com.profile.mapper.ProfileMapper;
import com.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ProfileController {
    ProfileService profileService;

    @PostMapping("/create")
    public ApiResponse<ProfileResponse> create(@RequestBody ProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.createProfile(request))
                .build();
    }

    @GetMapping("/getProfile/{profileId}")
    public ApiResponse<ProfileResponse> get(@PathVariable String profileId) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.getProfile(profileId))
                .build();
    }

    @GetMapping("/getAllProfile")
    public ApiResponse<List<ProfileResponse>> getAll() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.getAllProfile())
                .build();
    }

    @DeleteMapping("/deleteAll")
    public ApiResponse<?> deleteAll() {
        profileService.deleteAllProfile();
        return ApiResponse.builder()
                .message("Deleted !!")
                .build();
    }
}
