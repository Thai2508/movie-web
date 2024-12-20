package com.post.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Document("comment")
public class CommentEntity {
    @MongoId
    String id;
    String movieName;
    String nickName;
    String comment;
    Instant nowComment;
    @Builder.Default
    String dateComment="1 seconds ago";
    String isModifier;
}
