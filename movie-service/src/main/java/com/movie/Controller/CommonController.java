package com.movie.Controller;

import com.movie.dto.ApiResponse;
import com.movie.dto.response.CommonResponse;
import com.movie.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/getAll/")
public class CommonController {
    MovieService movieService;

    @GetMapping("{common}")
    public ApiResponse<List<CommonResponse>> getAll(@PathVariable String common) {
        return ApiResponse.<List<CommonResponse>>builder()
                .result(movieService.getAllCommon(common))
                .build();
    }
}
