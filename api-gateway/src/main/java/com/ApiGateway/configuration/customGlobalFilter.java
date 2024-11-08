package com.ApiGateway.configuration;

import com.ApiGateway.dto.ApiResponse;
import com.ApiGateway.repository.IdentityClient;
import com.ApiGateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class customGlobalFilter implements GlobalFilter, Ordered {
    ObjectMapper objectMapper;
    IdentityService identityService;

    @NonFinal
    private String[] publicEndpoints = {"/api/crud/authentication/.*", "/api/crud/signup"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isPublicEndpoints(exchange.getRequest()))
            return chain.filter(exchange);

        List<String> auth = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(auth))
            return authenticated(exchange.getResponse());

        return identityService.authenticated(auth.getFirst().replace("Bearer ",""))
                .flatMap(introspectResponse -> {
                    if (introspectResponse.getResult().isValid())
                        return chain.filter(exchange);
                    else
                        return authenticated(exchange.getResponse());
                }).onErrorResume(throwable -> authenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -7;
    }

    private boolean isPublicEndpoints(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints).anyMatch(p -> request.getURI().getPath().matches(p));
    }

    Mono<Void> authenticated(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        String str = "unAuthenticated";
        ApiResponse<?> api = ApiResponse.builder()
                .code(1108)
                .message(str)
                .build();
        try {
            str = objectMapper.writeValueAsString(api);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(str.getBytes())));
    }
}




