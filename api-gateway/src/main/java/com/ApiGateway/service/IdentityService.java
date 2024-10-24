package com.ApiGateway.service;

import com.ApiGateway.dto.ApiResponse;
import com.ApiGateway.dto.request.IntrospectRequest;
import com.ApiGateway.dto.response.IntrospectResponse;
import com.ApiGateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> authenticated(String token) {
        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
