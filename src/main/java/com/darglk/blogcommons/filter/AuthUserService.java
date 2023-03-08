package com.darglk.blogcommons.filter;

import com.darglk.blogcommons.model.UserResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class AuthUserService {

    private final RestTemplate restTemplate;
    private final String authServiceUrl;
    private final String authServicePort;

    public AuthUserService(String authServiceUrl, String authServicePort) {
        this.restTemplate = new RestTemplateBuilder()
                .build();
        this.authServiceUrl = authServiceUrl;
        this.authServicePort = authServicePort;
    }

    public UserResponse getUser(String userId) {
        final var url = String.format("http://%s:%s/api-internal/users/%s", authServiceUrl, authServicePort, userId);
        return restTemplate.getForEntity(url, UserResponse.class).getBody();
    }
}
