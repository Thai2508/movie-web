package com.Thai.CRUD.controller;

import com.Thai.CRUD.dto.request.ApiResponse;
import com.Thai.CRUD.dto.request.MovieRequest;
import com.Thai.CRUD.dto.response.MovieResponse;
import com.Thai.CRUD.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("movie")
public class MovieController {
    MovieService movieService;

    @PostMapping("/addMovie")
    public ApiResponse<MovieResponse> addMovie(@RequestBody MovieRequest movieRequest) {
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.addMovie(movieRequest))
                .build();
    }

    @GetMapping("/getMovies")
    public ApiResponse<List<MovieResponse>> getMovies() {
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.getMovies())
                .build();
    }

    @DeleteMapping("delete")
    public ApiResponse<Object> delete(String id) {
        return ApiResponse.builder()
                .message("Deleted !!!")
                .build();
    }
}
