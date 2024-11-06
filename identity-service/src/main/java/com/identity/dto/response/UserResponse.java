package com.identity.dto.response;

import com.identity.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String id;
    String username;
    String email;
    String lastName;
    String firstName;
    LocalDate dob;
    Set<RoleEntity> roles;
}
