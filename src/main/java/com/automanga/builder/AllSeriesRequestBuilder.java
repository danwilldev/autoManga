package com.automanga.builder;


import com.automanga.dtos.kavita.requests.AllSeriesRequest;
import com.automanga.dtos.kavita.requests.SortOptions;
import com.automanga.dtos.kavita.requests.Statement;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AllSeriesRequestBuilder {

    public AllSeriesRequest buildAllSeriesRequest() {
        return AllSeriesRequest.builder()
                .statements((Collections.singletonList(Statement.builder()
                        .comparison(0)
                        .value("")
                        .field(1).build())))
                .combination(0)
                .limitTo(0)
                .sortOptions(SortOptions.builder()
                        .isAscending(true)
                        .sortField(1).build())
                .build();
    }
}
