package com.automanga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "autobrr")
@Getter
@Setter
public class AutobrrConfig {

    private URI url;
}
