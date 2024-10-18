package com.profile.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("ProfileEntity")
public class ProfileEntity {
    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    String profileId;

    @Property("userId")
    String userId;

    String lastName;
    String firstName;
    LocalDate dob;

}
