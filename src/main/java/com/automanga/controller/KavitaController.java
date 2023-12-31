package com.automanga.controller;

import com.automanga.clients.KavitaClient;
import com.automanga.dtos.kavita.responses.SeriesTitleResponse;
import com.automanga.service.AutoMangaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kavita")
public class KavitaController {

    private final KavitaClient kavitaClient;
    private final AutoMangaService autoMangaService;

    public KavitaController(final KavitaClient kavitaClient, final AutoMangaService autoMangaService) {
        this.kavitaClient = kavitaClient;
        this.autoMangaService = autoMangaService;
    }

    @GetMapping("/getSeries")
    public Optional<List<SeriesTitleResponse>> getSeriesList() {
        return kavitaClient.makeRequestToKavita();
    }

    @GetMapping("/getTitles")
    public String getParsedTitles() {
        return autoMangaService.parseSeriesTitles();
    }
}
