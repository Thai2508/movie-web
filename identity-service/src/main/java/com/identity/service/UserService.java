package com.identity.service;

import com.event.dto.NotificationEvent;
import com.identity.dto.request.OtpEmailRequest;
import com.identity.dto.request.UserCreationRequest;
import com.identity.dto.request.UserUpdateRequest;
import com.identity.dto.response.OtpEmailResponse;
import com.identity.dto.response.UserResponse;
import com.identity.entity.OtpEmailEntity;
import com.identity.entity.UserEntity;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.mapper.ProfileMapper;
import com.identity.mapper.UserMapper;
import com.identity.repository.OtpEmailRepository;
import com.identity.repository.RoleRepository;
import com.identity.repository.UserRepository;
import com.identity.repository.httpClient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ProfileMapper profileMapper;
    ProfileClient profileClient;
    KafkaTemplate<String, Object> kafkaTemplate;
    OtpEmailRepository otpEmailRepository;

    public UserResponse createUser(UserCreationRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity user = userMapper.toUser(userRequest);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        var roles = roleRepository.findById("USER").stream().toList();
        user.setRoles(new HashSet<>(roles));

        user = userRepository.save(user);
        try {
            var profile = profileMapper.toProfileRequest(userRequest);
            profile.setUserId(user.getId());
            log.info("Profile: {}",user.getId());
            profileClient.createProfile(profile);
        } catch(Exception e){
            throw new AppException(ErrorCode.NOT_INITIALIZE);
        }

        return userMapper.toUserResponse(user);
    }

    public void sendingEmail() {
        var user = userRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        if (!user.getIsEmailAuth().equals("Unverified"))
            throw new AppException(ErrorCode.EMAIL_VERIFIED);

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
        otpEmailRepository.save(OtpEmailEntity.builder()
                .otp(String.valueOf(randomNum))
                .otpExpiryTime(Instant.now().plusSeconds(300))
                .build());
    }

    public OtpEmailResponse verifyEmail(OtpEmailRequest otpEmailRequest) {
        var user = userRepository.findById(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        OtpEmailEntity otpEmail = otpEmailRepository.findById(otpEmailRequest.getOtp())
                .orElseThrow(() -> new AppException(ErrorCode.WRONG_OTP));
        if (otpEmail.getOtpExpiryTime().isBefore(Instant.now())) {
            otpEmailRepository.deleteById(otpEmailRequest.getOtp());
            throw new AppException(ErrorCode.EMAIL_TIME);
        }
        user.setIsEmailAuth("Verified");
        userRepository.save(user);
        return OtpEmailResponse.builder().mess("Verify SuccessFull !!").build();
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

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        userRepository.deleteById(id);
        kafkaTemplate.send("delete-profile", id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllUser() {
        userRepository.deleteAll();
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
