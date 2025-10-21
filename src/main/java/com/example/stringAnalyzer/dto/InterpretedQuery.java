package com.example.stringAnalyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class InterpretedQuery {

    private String original;

    @JsonProperty("parsed_filters")
    private Map<String, Object> parsedFilters;

    public InterpretedQuery(String query, Map<String, Object> parsedFilters) {
    }
}
