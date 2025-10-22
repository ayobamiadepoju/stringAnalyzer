package com.example.stringAnalyzer.service;

import com.example.stringAnalyzer.dto.InterpretedQuery;
import com.example.stringAnalyzer.dto.NaturalLanguageResponse;
import com.example.stringAnalyzer.dto.StringProperties;
import com.example.stringAnalyzer.dto.StringResponse;
import com.example.stringAnalyzer.model.AnalyzedString;
import com.example.stringAnalyzer.storage.StringStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NaturalLanguageProcessor {
    @Autowired
    private StringStorage storage;

    @Autowired
    private StringAnalyzerService analyzerService;

    public NaturalLanguageResponse processNaturalLanguageQuery(String query) {

        query = query.toLowerCase().trim();

        Map<String, Object> parsedFilters = new HashMap<>();

        // Parse palindrome queries
        if (query.contains("palindrome")) {
            parsedFilters.put("is_palindrome", true);
        }

        // Parse word count
        if (query.contains("single word")) {
            parsedFilters.put("word_count", 1);
        } else if (query.contains("two word")) {
            parsedFilters.put("word_count", 2);
        }

        // Parse length - "longer than X characters"
        Pattern longerThanPattern = Pattern.compile("longer than (\\d+)");
        Matcher matcher = longerThanPattern.matcher(query);
        if (matcher.find()) {
            int length = Integer.parseInt(matcher.group(1));
            parsedFilters.put("min_length", length + 1);
        }

        // Parse character containment
        Pattern containsPattern = Pattern.compile("contain(?:ing)?.*?letter\\s+([a-z])");
        Matcher containsMatcher = containsPattern.matcher(query);
        if (containsMatcher.find()) {
            parsedFilters.put("contains_character", containsMatcher.group(1));
        }

        // Special case: "first vowel" = 'a'
        if (query.contains("first vowel")) {
            parsedFilters.put("contains_character", "a");
        }

        // Apply filters
        List<AnalyzedString> results = applyFilters(parsedFilters);

        // Convert to response
        List<StringResponse> data = results.stream()
                .map(this::convertToResponse)
                .toList();

        // Build response
        NaturalLanguageResponse response = new NaturalLanguageResponse();
        response.setData(data);
        response.setCount(data.size());
        response.setInterpretedQuery(
                new InterpretedQuery(query ,parsedFilters)
        );

        return response;
    }

    // Apply parsed filters to storage
    private List<AnalyzedString> applyFilters(Map<String, Object> filters) {
        Boolean isPalindrome = (Boolean) filters.get("is_palindrome");
        Integer minLength = (Integer) filters.get("min_length");
        Integer maxLength = (Integer) filters.get("max_length");
        Integer wordCount = (Integer) filters.get("word_count");
        String containsCharacter = (String) filters.get("contains_character");

        // Get from storage
        List<AnalyzedString> results = storage.findByFilters(
                isPalindrome, minLength, maxLength, wordCount
        );

        // Post-filter for character containment
        if (containsCharacter != null && !containsCharacter.isEmpty()) {
            results = results.stream()
                    .filter(s -> s.getValue().toLowerCase()
                            .contains(containsCharacter.toLowerCase()))
                    .toList();
        }

        return results;
    }

    private StringResponse convertToResponse(AnalyzedString analyzed) {
        StringProperties properties = new StringProperties(
                analyzed.getLength(),
                analyzed.getIsPalindrome(),
                analyzed.getUniqueCharacters(),
                analyzed.getWordCount(),
                analyzed.getSha256Hash(),
                analyzed.getCharacterFrequencyMap()
        );

        return new StringResponse(
                analyzed.getId(),
                analyzed.getValue(),
                properties,
                analyzed.getCreatedAt()
        );
    }
}