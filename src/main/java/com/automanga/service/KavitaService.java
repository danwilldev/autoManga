package com.automanga.service;

import com.automanga.clients.KavitaClient;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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
                .map(title -> formatForAutobrr(title.getName()) + ", " + formatForAutobrr(title.getLocalizedName()))
                .collect(Collectors.toList());

        log.info(String.valueOf(names).replace("[", "").replace("]", ""));
        return (names);
    }
}
