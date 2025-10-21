package com.example.stringAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringListResponse {

    private List<StringResponse> data;
    private Integer count;
    private Map<String, Object> filtersApplied;
}
