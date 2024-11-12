package com.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String email;
<<<<<<< Updated upstream
    @Builder.Default
    String isEmailAuth="Unverified";
=======
<<<<<<< Updated upstream
>>>>>>> Stashed changes
    String lastName;
    String firstName;
=======
    String isEmailAuth;
    String nickName;
>>>>>>> Stashed changes
    LocalDate dob;

    @ManyToMany
    Set<RoleEntity> roles;
}
