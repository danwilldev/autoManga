package com.automanga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "releases")
@Getter
@Setter
public class ReleaseConfig {

    private ReleaseGroup releaseGroup;

    @Getter
    @Setter
    public static class ReleaseGroup {

        private String name;
        private String position;
    }
}
