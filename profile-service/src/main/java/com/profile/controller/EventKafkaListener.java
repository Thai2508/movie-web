package com.profile.controller;

import com.profile.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@KafkaListener(groupId = "profile-group", topics = {"delete-profile"})
@Slf4j
public class EventKafkaListener {
    ProfileService profileService;

    @KafkaHandler
    public void listenUserProfile(String id) {
        profileService.deleteProfile(id);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Unknown Event {}",object);
    }
}
