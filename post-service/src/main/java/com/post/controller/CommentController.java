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

    @PostMapping("/")
    public ApiResponse<CommentResponse> comment(@RequestBody CommentRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

    @GetMapping("/getComment/{movieId}")
    public ApiResponse<List<CommentResponse>> getComment(@PathVariable String movieId) {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getCommentMovie(movieId))
                .build();
    }
}
