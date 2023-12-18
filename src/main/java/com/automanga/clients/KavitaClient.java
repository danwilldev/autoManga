package com.automanga.clients;


import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import com.automanga.dtos.kavita.requests.AllSeriesRequest;
import com.automanga.dtos.kavita.requests.SortOptions;
import com.automanga.dtos.kavita.requests.Statement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class KavitaClient {
    private final RestTemplate restTemplate;

    @Value("${kavita.url}")
    private URI kavitaUrl;


    public KavitaClient(final RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

        public Optional<List<SeriesTitleResponse>> makeRequestToKavita() {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth("");

            AllSeriesRequest allSeriesRequest = AllSeriesRequest.builder()
                .statements((Collections.singletonList(Statement.builder()
                        .comparison(0)
                        .value("")
                        .field(1).build())))
                .combination(0)
                .limitTo(0)
                .sortOptions(SortOptions.builder()
                        .isAscending(true)
                        .sortField(1).build())
                .build();


            HttpEntity<AllSeriesRequest> httpEntity = new HttpEntity<>(allSeriesRequest, headers);

            final ResponseEntity<List<SeriesTitleResponse>> responseEntity = restTemplate.exchange(kavitaUrl,HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

            return Optional.ofNullable(responseEntity.getBody());

        }

    }
