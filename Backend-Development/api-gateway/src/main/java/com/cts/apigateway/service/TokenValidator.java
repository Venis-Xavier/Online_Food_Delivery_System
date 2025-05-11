package com.cts.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TokenValidator {
    private final WebClient webClient;

    public TokenValidator(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085").build();
    }

    public Mono<Boolean> validateToken(String token) {
        return webClient.post()
                .uri("/api/auth/validate")
                .bodyValue(token)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}

