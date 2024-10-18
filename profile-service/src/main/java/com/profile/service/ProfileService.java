package com.profile.service;

import com.profile.dto.request.ProfileRequest;
import com.profile.dto.response.ProfileResponse;
import com.profile.entity.ProfileEntity;
import com.profile.mapper.ProfileMapper;
import com.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProfileService {
    ProfileMapper profileMapper;
    ProfileRepository profileRepository;

    public ProfileResponse createProfile(ProfileRequest request) {
        var  profile = profileMapper.toProfileEntity(request);

        return profileMapper.toProfileResponse(profileRepository.save(profile));
    }

    public ProfileResponse getProfile(String profileId) {
        var profile = profileRepository.findById(profileId).
                orElseThrow(() -> new RuntimeException("No Found Id"));
        return profileMapper.toProfileResponse(profile);
    }

    public List<ProfileResponse> getAllProfile() {
        return profileRepository.findAll().stream().map(profileMapper::toProfileResponse).toList();
    }

    public void deleteAllProfile() {
        profileRepository.deleteAll();
    }
}
