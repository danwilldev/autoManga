package com.automanga.dtos.kavita.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllSeriesRequest {
    private List<Statement> statements;
    private int combination;
    private int limitTo;
    private SortOptions sortOptions;
}

