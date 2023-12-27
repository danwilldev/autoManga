package com.automanga.service;

import com.automanga.clients.KavitaClient;
import com.automanga.config.KavitaConfig;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class KavitaService {

    private final KavitaClient kavitaClient;
    private final KavitaConfig kavitaConfig;

    public KavitaService(final KavitaClient kavitaClient, KavitaConfig kavitaConfig) {
        this.kavitaClient = kavitaClient;
        this.kavitaConfig = kavitaConfig;
    }

    private static String formatForAutobrr(String input) {
        String output = "*" + input + "*";
        return output.replace(' ', '?');
    }

    private static String addReleaseGroup(String input, String position, String group) {
        if (!position.isEmpty()) {
            if (position.equals("before")) {
                return "*" + group + input;
            } else if (position.equals("after")) {
                return input + group + "*";
            } else {
                log.error("Position set to invalid value, check config.");
                throw new InvalidParameterException();
            }
        }
        String output = "*" + input + "*";
        return output.replace(' ', '?');
    }

    public List<String> parseSeriesTitles() {

        Optional<List<SeriesTitleResponse>> titles = kavitaClient.makeRequestToKavita();

        List<String> names = titles
                .orElse(List.of())
                .stream()
                .filter(title -> kavitaConfig.getLibrary().getIds().contains(title.getLibraryId()))
                .map(title -> formatForAutobrr(title.getName()) + ", " + formatForAutobrr(title.getLocalizedName()))
                .toList();

        log.info(String.valueOf(names).replace("[", "").replace("]", ""));
        return (names);
    }
}
