package com.identity.service;

import com.event.dto.NotificationEvent;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.request.UserUpdateRequest;
import com.identity.dto.response.UserResponse;
import com.identity.entity.UserEntity;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.mapper.ProfileMapper;
import com.identity.mapper.UserMapper;
import com.identity.repository.RoleRepository;
import com.identity.repository.UserRepository;
import com.identity.repository.httpClient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

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
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserResponse createUser(UserCreationRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity user;
        try {
            user = userMapper.toUser(userRequest);
            var profile = profileMapper.toProfileRequest(userRequest);
            profile.setUserId(user.getId());
            profileClient.createProfile(profile);
        } catch(Exception e){
            throw new AppException(ErrorCode.NOT_INITIALIZE);
        }
        Random random = new Random();
        int randomNum = random.nextInt(90000)+10000;
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL-AUTHENTICATION")
                .recipient(user.getEmail())
                .subject("Email Authentication Code")
                .body("Your Authentication Code is : "+randomNum)
                .build();
        // Publish message to kafka
        kafkaTemplate.send("notification-delivery", notificationEvent);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        var roles = roleRepository.findById("USER").stream().toList();
        user.setRoles(new HashSet<>(roles));

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest) {
        var id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findById(id)
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
        var id = SecurityContextHolder.getContext().getAuthentication().getName();

        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_AUTH)));
    }

//    public UserResponse getUser(String id) {
//        return userMapper.toUserResponse(userRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
//    }
}
