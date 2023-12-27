package com.automanga.controller;

import com.automanga.clients.AutobrrClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autobrr")
public class AutobrrController {

    private final AutobrrClient autobrrClient;

    public AutobrrController(AutobrrClient autobrrClient) {
        this.autobrrClient = autobrrClient;
    }

    @GetMapping("/getUpdate")
    public void get() {
        autobrrClient.updateFilter();
    }
}
