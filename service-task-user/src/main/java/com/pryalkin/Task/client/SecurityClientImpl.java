package com.pryalkin.Task.client;

import com.pryalkin.Task.dto.request.AuthServerRequestDTO;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;
import com.pryalkin.Task.dto.response.AuthServerResponseDTO;
import com.pryalkin.Task.dto.response.AuthorizationResponseDTO;
import com.pryalkin.Task.dto.response.HttpResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class SecurityClientImpl implements SecurityClient{

    private final WebClient webClient = WebClient.create();

    @Override
    public AuthorizationResponseDTO authorization(AuthorizationRequestDTO authorizationRequestDTO) {
        AuthorizationResponseDTO result = webClient.post()
                .uri("http://localhost:8080/auth/authorization/user")
                .bodyValue(authorizationRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationRequestDTO.getServiceToken())
                .retrieve()
                .bodyToMono(AuthorizationResponseDTO.class)
                .block();
        return result;
    }

    @Override
    public AuthServerResponseDTO registrationServer(AuthServerRequestDTO authServerRequestDTO) {
        AuthServerResponseDTO result = webClient.post()
                .uri("http://localhost:8080/auth/registration/server")
                .bodyValue(authServerRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(AuthServerResponseDTO.class)
                .block();
        return result;
    }

    @Override
    public AuthServerResponseDTO authorizationServer(AuthServerRequestDTO authServerRequestDTO) {
        AuthServerResponseDTO result = webClient.post()
                .uri("http://localhost:8080/auth/authorization/server")
                .bodyValue(authServerRequestDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(AuthServerResponseDTO.class)
                .block();
        return result;
    }

}
