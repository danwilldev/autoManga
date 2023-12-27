package com.automanga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "kavita")
@Getter
@Setter
public class KavitaConfig {

    private URI url;
    private Library library;
    private String apiKey;

    @Getter
    @Setter
    public static class Library {

        private List<Integer> ids;
    }
}
