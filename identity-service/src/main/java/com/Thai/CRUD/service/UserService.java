package com.Thai.CRUD.service;

import com.Thai.CRUD.dto.request.ProfileRequest;
import com.Thai.CRUD.dto.request.UserCreationRequest;
import com.Thai.CRUD.dto.request.UserUpdateRequest;
import com.Thai.CRUD.dto.response.UserResponse;
import com.Thai.CRUD.entity.UserEntity;
import com.Thai.CRUD.exception.AppException;
import com.Thai.CRUD.exception.ErrorCode;
import com.Thai.CRUD.mapper.ProfileMapper;
import com.Thai.CRUD.mapper.UserMapper;
import com.Thai.CRUD.repository.PermissionRepository;
import com.Thai.CRUD.repository.RoleRepository;
import com.Thai.CRUD.repository.UserRepository;
import com.Thai.CRUD.repository.httpClient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ProfileMapper profileMapper;
    ProfileClient profileClient;

    public UserResponse createUser(UserCreationRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        UserEntity user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        var roles = roleRepository.findById("USER").stream().toList();
        user.setRoles(new HashSet<>(roles));
        user = userRepository.save(user);

        var profile = profileMapper.toProfileRequest(userRequest);
        profile.setUserId(user.getId());

        profileClient.createProfile(profile);


        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.userUpdate(user,userUpdateRequest);
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }


    public void deleteUser(String id) {
        userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var name = SecurityContextHolder.getContext().getAuthentication().getName();

        return userMapper.toUserResponse(userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_AUTH)));
    }

//    public UserResponse getUser(String id) {
//        return userMapper.toUserResponse(userRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
//    }
}
