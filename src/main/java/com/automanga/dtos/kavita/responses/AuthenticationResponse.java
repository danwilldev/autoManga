package com.automanga.dtos.kavita.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class AuthenticationResponse {

    private String username;
    private String token;
    private String refreshToken;
    private String apiKey;
}
