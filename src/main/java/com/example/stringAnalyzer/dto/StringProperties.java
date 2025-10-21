package com.example.stringAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringProperties {

    private int length;
    private boolean isPalindrome;
    private int uniqueCharacters;
    private int wordCount;
    private String sha256Hash;
    private Map<String, Integer> characterFrequencyMap;
}
