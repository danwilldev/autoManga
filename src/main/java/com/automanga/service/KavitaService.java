package com.automanga.service;

import com.automanga.clients.KavitaClient;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KavitaService {

    private final KavitaClient kavitaClient;

    public KavitaService(final KavitaClient kavitaClient) {
        this.kavitaClient = kavitaClient;
    }

    private static String formatForAutobrr(String input) {
        String output = "*" + input + "*";
        return output.replace(' ', '?');
    }

    public List<String> parseSeriesTitles() {

        Optional<List<SeriesTitleResponse>> titles = kavitaClient.makeRequestToKavita();

        List<String> names = titles
                .orElse(List.of())
                .stream()
                .map(title -> formatForAutobrr(title.getName()) + ", " + formatForAutobrr(title.getLocalizedName()))
                .collect(Collectors.toList());

        log.info(String.valueOf(names));
        return (names);
    }
}
