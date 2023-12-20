package com.automanga.controller;

import com.automanga.clients.KavitaClient;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import com.automanga.service.KavitaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kavita")
public class KavitaController {

    private final KavitaClient kavitaClient;
    private final KavitaService kavitaService;

    public KavitaController(final KavitaClient kavitaClient, final KavitaService kavitaService) {
        this.kavitaClient = kavitaClient;
        this.kavitaService = kavitaService;
    }

    @GetMapping("/getSeries")
    public Optional<List<SeriesTitleResponse>> getSeriesList() {
        return kavitaClient.makeRequestToKavita();
    }

    @GetMapping("/getTitles")
    public List<String> getParsedTitles() {
        return kavitaService.parseSeriesTitles();
    }

    @GetMapping("/getJwt")
    public String get() {
        return kavitaClient.getKavitaJwt();
    }
}
