package com.post.controller;

import com.post.entity.MovieEntity;
import com.post.entity.UserEntity;
import com.post.repository.MovieRepository;
import com.post.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Slf4j
public class EventKafkaListener {
    UserRepository userRepository;
    MovieRepository movieRepository;

    @KafkaListener(groupId = "nickName", topics = {"getNickName"})
    public void getNickName(List<String> list) {
        userRepository.save(UserEntity.builder()
                        .id(list.getFirst())
                        .nickName(list.getLast())
                .build());
    }

    @KafkaListener(groupId = "movieName", topics = {"getMovieName"})
    public void getMovieName(List<String> list) {
        movieRepository.save(MovieEntity.builder()
                .id(list.getFirst())
                .movieName(list.getLast())
                .build());
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        log.info("Unknown Event {}",object);
    }
}
