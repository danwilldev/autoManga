package com.automanga.controller;

import com.automanga.clients.KavitaClient;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kavita")
public class KavitaController {

    private final KavitaClient kavitaClient;

    public KavitaController(final KavitaClient kavitaClient) {
        this.kavitaClient = kavitaClient;
    }

    @GetMapping("/getSeries")
    public Optional<List<SeriesTitleResponse>> getSeriesList() {
        return kavitaClient.makeRequestToKavita();
    }
}
