package com.example.stringAnalyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class InterpretedQuery {

    private String original;

    @JsonProperty("parsed_filters")
    private Map<String, Object> parsedFilters;

    public InterpretedQuery(String query, Map<String, Object> parsedFilters) {
        this.original = query;
        this.parsedFilters = parsedFilters;
    }
}
