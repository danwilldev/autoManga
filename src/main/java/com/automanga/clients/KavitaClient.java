package com.automanga.clients;


import com.automanga.builder.AllSeriesRequestBuilder;
import com.automanga.dtos.kavita.requests.AllSeriesRequest;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class KavitaClient {
    private final RestTemplate restTemplate;
    private AllSeriesRequestBuilder allSeriesRequestBuilder;

    @Value("${kavita.url}")
    private URI kavitaUrl;
    @Value("${kavita.token}")
    private String kavitaToken;


    public KavitaClient(final RestTemplateBuilder restTemplateBuilder, AllSeriesRequestBuilder allSeriesRequestBuilder){
        this.restTemplate = restTemplateBuilder.build();
        this.allSeriesRequestBuilder = allSeriesRequestBuilder;
    }

        public Optional<List<SeriesTitleResponse>> makeRequestToKavita() {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(kavitaToken);

            AllSeriesRequest allSeriesRequest = allSeriesRequestBuilder.buildAllSeriesRequest();

            HttpEntity<AllSeriesRequest> httpEntity = new HttpEntity<>(allSeriesRequest, headers);
            final ResponseEntity<List<SeriesTitleResponse>> responseEntity = restTemplate.exchange(kavitaUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {});
            return Optional.ofNullable(responseEntity.getBody());
        }
    }
