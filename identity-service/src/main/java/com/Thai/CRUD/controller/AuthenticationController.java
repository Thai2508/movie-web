package com.Thai.CRUD.controller;

import com.Thai.CRUD.dto.request.ApiResponse;
import com.Thai.CRUD.dto.request.AuthenticationRequest;
import com.Thai.CRUD.dto.request.IntrospectRequest;
import com.Thai.CRUD.dto.response.AuthenticationResponse;
import com.Thai.CRUD.dto.response.IntrospectResponse;
import com.Thai.CRUD.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("authentication")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        var auth = authenticationService.authenticated(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(auth)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> validToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var auth = authenticationService.validToken(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(auth)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logoutToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);

        return ApiResponse.builder()
                .message("Successful !!")
                .build();
    }
}
