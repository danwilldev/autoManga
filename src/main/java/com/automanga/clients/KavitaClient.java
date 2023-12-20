package com.automanga.clients;

import com.automanga.builder.AllSeriesRequestBuilder;
import com.automanga.dtos.kavita.requests.AllSeriesRequest;
import com.automanga.dtos.kavita.responses.AuthenticationResponse;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class KavitaClient {

    private final RestTemplate restTemplate;
    private final AllSeriesRequestBuilder allSeriesRequestBuilder;
    @Value("${kavita.url}")
    private URI kavitaUrl;
    @Value("${kavita.token}")
    private String kavitaToken;
    @Value("${kavita.api.key}")
    private String apiKey;

    public KavitaClient(final RestTemplateBuilder restTemplateBuilder, AllSeriesRequestBuilder allSeriesRequestBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.allSeriesRequestBuilder = allSeriesRequestBuilder;
    }

    public String getKavitaJwt() {
        kavitaUrl = URI.create(kavitaUrl + "api/Plugin/authenticate");
        URI url = UriComponentsBuilder.fromUri(kavitaUrl)
                .queryParam("apiKey", apiKey)
                .queryParam("pluginName", "AutoManga")
                .build().toUri();

        AuthenticationResponse response = restTemplate.postForObject(url, null, AuthenticationResponse.class);
        log.info("Fetched JWT for {}", Objects.requireNonNull(response).getUsername());

        return Objects.requireNonNull(restTemplate.postForObject(url, null, AuthenticationResponse.class)).getToken();
    }

    public Optional<List<SeriesTitleResponse>> makeRequestToKavita() {
        URI url = URI.create(kavitaUrl + "api/Series/all-v2");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(kavitaToken);

        AllSeriesRequest allSeriesRequest = allSeriesRequestBuilder.buildAllSeriesRequest();

        HttpEntity<AllSeriesRequest> httpEntity = new HttpEntity<>(allSeriesRequest, headers);
        final ResponseEntity<List<SeriesTitleResponse>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });
        return Optional.ofNullable(responseEntity.getBody());
    }
}
