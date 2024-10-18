package com.profile.controller;

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
    public ProfileResponse create(@RequestBody ProfileRequest request) {
        return profileService.createProfile(request);
    }

    @GetMapping("/getProfile/{profileId}")
    public ProfileResponse get(@PathVariable String profileId) {
        return profileService.getProfile(profileId);
    }

    @GetMapping("/getAllProfile")
    public List<ProfileResponse> getAll() {
        return profileService.getAllProfile();
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        profileService.deleteAllProfile();
    }
}
