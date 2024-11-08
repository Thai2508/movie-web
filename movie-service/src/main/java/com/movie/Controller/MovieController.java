package com.movie.Controller;

import com.movie.dto.ApiResponse;
import com.movie.dto.request.MovieRequest;
import com.movie.dto.response.MovieResponse;
import com.movie.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MovieController {
    MovieService movieService;

    @PostMapping("/create")
    public ApiResponse<MovieResponse> addMovie(@RequestBody MovieRequest request) {
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.createMovie(request))
                .build();
    }

    @GetMapping("/getInfoMovie/{movieId}")
    public ApiResponse<MovieResponse> getInfo(@PathVariable String movieId) {
        return ApiResponse.<MovieResponse>builder()
                .result(movieService.getInfoMovie(movieId))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<MovieResponse>> getAll() {
        return ApiResponse.<List<MovieResponse>>builder()
                .result(movieService.getAllMovie())
                .build();
    }

    @DeleteMapping("/deleteAll")
    public ApiResponse<?> deleteAll() {
        movieService.deleteAll();
        return ApiResponse.builder()
                .message("Deleted !!")
                .build();
    }

}
