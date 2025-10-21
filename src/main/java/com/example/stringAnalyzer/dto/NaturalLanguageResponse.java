package com.example.stringAnalyzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaturalLanguageResponse {

    private List<StringResponse> data;
    private Integer count;

    @JsonProperty("interpreted_query")
    private InterpretedQuery interpretedQuery;
}
