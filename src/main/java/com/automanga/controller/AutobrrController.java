package com.automanga.controller;

import com.automanga.clients.AutobrrClient;
import com.automanga.service.AutoMangaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autobrr")
public class AutobrrController {

    private final AutobrrClient autobrrClient;
    private final AutoMangaService autoMangaService;

    public AutobrrController(AutobrrClient autobrrClient, AutoMangaService autoMangaService) {
        this.autobrrClient = autobrrClient;
        this.autoMangaService = autoMangaService;
    }

    @GetMapping("/updateFilter")
    public void get() {
        autobrrClient.updateFilter(autoMangaService.parseSeriesTitles());
    }
}
