package com.post.controller;

import com.post.dto.ApiResponse;
import com.post.dto.request.CommentRequest;
import com.post.dto.response.CommentResponse;
import com.post.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    CommentService commentService;

    @PostMapping("/{movieId}")
    public ApiResponse<CommentResponse> comment(@PathVariable String movieId, @RequestBody CommentRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(request,movieId))
                .build();
    }

    @GetMapping("/getComment/{id}")
    public ApiResponse<List<CommentResponse>> getComment(@PathVariable String id) {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getCommentMovie(id))
                .build();
    }

    @DeleteMapping("/deleteAll")
    public ApiResponse<?> deleteAll() {
        commentService.deleteAll();
        return ApiResponse.builder()
                .message("Success Full !!")
                .build();
    }
}
