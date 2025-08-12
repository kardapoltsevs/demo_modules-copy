package com.example.user.config;

import com.example.user.service.TokenService;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestClientAuthInterceptor implements ClientHttpRequestInterceptor {
    private final TokenService tokenService;

    public RestClientAuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        String token = tokenService.getCurrentAuthToken();
        request.getHeaders().set("Authorization", "Bearer " + token);
        return execution.execute(request, body);
    }
}
