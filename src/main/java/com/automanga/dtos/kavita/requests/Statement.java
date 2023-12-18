package com.automanga.dtos.kavita.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statement {
    private int comparison;
    private String value;
    private int field;
}