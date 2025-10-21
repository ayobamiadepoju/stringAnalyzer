package com.example.stringAnalyzer.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzedString {

    private String id;
    private String value;
    private Integer length;
    private Boolean isPalindrome;
    private Integer uniqueCharacters;
    private Integer wordCount;
    private String sha256Hash;
    private Map<String, Integer> characterFrequencyMap;
    private LocalDateTime createdAt;

    public AnalyzedString(String value) {
        this.value = value;
        this.createdAt = LocalDateTime.now();
    }
}
