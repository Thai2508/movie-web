package com.identity.config;

import com.identity.entity.RoleEntity;
import com.identity.entity.UserEntity;
import com.identity.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

<<<<<<< Updated upstream
import java.util.HashSet;

=======
<<<<<<< Updated upstream
=======
import java.util.HashSet;
import java.util.List;

>>>>>>> Stashed changes
>>>>>>> Stashed changes

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@Slf4j
//@EnableFeignClients(basePackages = "com.Thai.CRUD.repository.httpClient")
public class AppInitConfig {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .build();

                userRepository.save(user);
                List<String> list = List.of(user.getId(),user.getUsername());
                log.info("{}",list);
                kafkaTemplate.send("getNickName", list);
            }
        };
    }

}
