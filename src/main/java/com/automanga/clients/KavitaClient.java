package com.automanga.clients;

import com.automanga.builder.AllSeriesRequestBuilder;
import com.automanga.config.KavitaConfig;
import com.automanga.dtos.kavita.requests.AllSeriesRequest;
import com.automanga.dtos.kavita.responses.AuthenticationResponse;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@EnableRetry
public class KavitaClient {

    private final RestTemplate restTemplate;
    private final AllSeriesRequestBuilder allSeriesRequestBuilder;
    private final KavitaConfig kavitaConfig;

    public KavitaClient(final RestTemplateBuilder restTemplateBuilder, AllSeriesRequestBuilder allSeriesRequestBuilder, final KavitaConfig kavitaConfig) {
        this.restTemplate = restTemplateBuilder.build();
        this.allSeriesRequestBuilder = allSeriesRequestBuilder;
        this.kavitaConfig = kavitaConfig;
    }

    @Retryable(backoff = @Backoff(delay = 5000))
    public String getKavitaJwt() {
        URI kavitaUrl = URI.create(kavitaConfig.getUrl() + "api/Plugin/authenticate");
        URI url = UriComponentsBuilder.fromUri(kavitaUrl)
                .queryParam("apiKey", kavitaConfig.getApiKey())
                .queryParam("pluginName", "AutoManga")
                .build().toUri();

        AuthenticationResponse response = restTemplate.postForObject(url, null, AuthenticationResponse.class);
        log.info("Fetched JWT for {}", Objects.requireNonNull(response).getUsername());

        return Objects.requireNonNull(restTemplate.postForObject(url, null, AuthenticationResponse.class)).getToken();
    }

    @Retryable(backoff = @Backoff(delay = 5000))
    public Optional<List<SeriesTitleResponse>> makeRequestToKavita() {
        URI url = URI.create(kavitaConfig.getUrl() + "api/Series/all-v2");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getKavitaJwt());

        AllSeriesRequest allSeriesRequest = allSeriesRequestBuilder.buildAllSeriesRequest();

        HttpEntity<AllSeriesRequest> httpEntity = new HttpEntity<>(allSeriesRequest, headers);
        final ResponseEntity<List<SeriesTitleResponse>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });
        return Optional.ofNullable(responseEntity.getBody());
    }
}
