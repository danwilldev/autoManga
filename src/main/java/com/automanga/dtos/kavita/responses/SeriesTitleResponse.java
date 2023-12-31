package com.automanga.dtos.kavita.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class SeriesTitleResponse {

    private int id;
    private String name;
    private String localizedName;
    private int libraryId;
}
