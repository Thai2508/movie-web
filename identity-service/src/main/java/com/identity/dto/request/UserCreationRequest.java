package com.identity.dto.request;

import com.identity.validator.DobConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
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
public class UserCreationRequest {
    @Size(min = 2, message = "USERNAME_SIZE")
    String username;
    @Size(min = 5, message = "PASSWORD_SIZE")
    String password;
    String lastName;
    String firstName;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    Set<String> roles;
}
