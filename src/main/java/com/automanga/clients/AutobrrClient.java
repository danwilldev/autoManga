package com.automanga.clients;

import com.automanga.config.AutobrrConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class AutobrrClient {

    private final RestTemplate restTemplate;
    private final AutobrrConfig autobrrConfig;

    public AutobrrClient(RestTemplateBuilder restTemplateBuilder, AutobrrConfig autobrrConfig) {
        this.restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.autobrrConfig = autobrrConfig;
    }

    @Retryable(noRetryFor = HttpClientErrorException.class, backoff = @Backoff(delay = 5000))
    public void updateFilter(String titles) {
        URI url = URI.create(autobrrConfig.getUrl() + "/api/filters/" + autobrrConfig.getFilterId());
        final HttpHeaders headers = getHeaders();

        String requestJson = String.format("{\"match_releases\":\"%s\"}", titles);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, String.class);
        } catch (HttpClientErrorException e) {
            log.error("Http Client Error", e);
        } catch (HttpServerErrorException e) {
            log.error("Http Server Error", e);
        } catch (Exception e) {
            log.error("Error updating autobrr filter", e);
        }
    }

    private HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Token", autobrrConfig.getApiKey());
        if (Boolean.TRUE.equals(autobrrConfig.getBasicAuth())) {
            headers.setBasicAuth(autobrrConfig.getUsername(), autobrrConfig.getPassword());
        }
        return headers;
    }
}
