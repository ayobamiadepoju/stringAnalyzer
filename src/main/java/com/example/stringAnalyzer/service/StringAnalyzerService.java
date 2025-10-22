package com.example.stringAnalyzer.service;

import com.example.stringAnalyzer.dto.StringListResponse;
import com.example.stringAnalyzer.dto.StringProperties;
import com.example.stringAnalyzer.dto.StringResponse;
import com.example.stringAnalyzer.exception.StringAlreadyExistsException;
import com.example.stringAnalyzer.exception.StringNotFoundException;
import com.example.stringAnalyzer.model.AnalyzedString;
import com.example.stringAnalyzer.storage.StringStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StringAnalyzerService {

    @Autowired
    StringStorage storage;

    public StringResponse createAndSave(String value) throws NoSuchAlgorithmException {
        if (storage.existByValue(value)){
            try {
                throw new StringAlreadyExistsException("String already exist");
            } catch (StringAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        AnalyzedString analyzedString = analyzeString(value);
        storage.save(analyzeString(value));
        return convertToResponse(analyzedString);
    }

    public StringResponse findString(String value) throws NoSuchAlgorithmException, StringNotFoundException {
        AnalyzedString analyzedString = null;

            analyzedString = storage.findByValue(value)
                    .orElseThrow(() -> new StringNotFoundException("String does not exist"));

        return convertToResponse(analyzedString);
    }

    public StringListResponse getAllStrings(
            Boolean isPalindrome,
            Integer minLength,
            Integer maxLength,
            Integer wordCount,
            String containsCharacter
    ){
        List<AnalyzedString> result = storage.findByFilters(isPalindrome, minLength, maxLength, wordCount);

        if (containsCharacter != null) {
            result = result.stream()
                    .filter(s -> s.getValue().toLowerCase()
                            .contains(containsCharacter.toLowerCase()))
                    .toList();
        }

        List<StringResponse> data = result.stream()
                .map(this::convertToResponse)
                .toList();

        Map<String, Object> filtersApplied = buildFiltersMap(isPalindrome, minLength, maxLength, wordCount, containsCharacter
        );

        return new StringListResponse(data, data.size(), filtersApplied);
    }

    public void deleteString(String value) {
        boolean deleted = storage.deleteByValue(value);
        if (!deleted) {
            try {
                throw new StringNotFoundException("String not found");
            } catch (StringNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AnalyzedString analyzeString(String value) throws NoSuchAlgorithmException {
        AnalyzedString analyzed = new AnalyzedString(value);

        String hash = calculateSHA256(value);
        analyzed.setId(hash);
        analyzed.setSha256Hash(hash);
        analyzed.setLength(value.length());
        analyzed.setIsPalindrome(isPalindrome(value));
        analyzed.setUniqueCharacters(countUniqueCharacters(value));
        analyzed.setWordCount(countWords(value));
        analyzed.setCharacterFrequencyMap(buildCharacterFrequencyMap(value));

        return analyzed;
    }

    // Calculate SHA-256 hash
    private String calculateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Check if palindrome (case-insensitive, alphanumeric only)
    private boolean isPalindrome(String str) {
        String cleaned = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    // Count unique characters
    private int countUniqueCharacters(String str) {
        return (int) str.chars().distinct().count();
    }

    // Count words (split by whitespace)
    private int countWords(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        return str.trim().split("\\s+").length;
    }

    // Build character frequency map
    private Map<String, Integer> buildCharacterFrequencyMap(String str) {
        Map<String, Integer> frequencyMap = new LinkedHashMap<>();
        for (char c : str.toCharArray()) {
            String key = String.valueOf(c);
            frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
        }
        return frequencyMap;
    }

    private Map<String, Object> buildFiltersMap(
            Boolean isPalindrome,
            Integer minLength,
            Integer maxLength,
            Integer wordCount,
            String containsCharacter) {

        Map<String, Object> filters = new HashMap<>();

        if (isPalindrome != null) {
            filters.put("is_palindrome", isPalindrome);
        }
        if (minLength != null) {
            filters.put("min_length", minLength);
        }
        if (maxLength != null) {
            filters.put("max_length", maxLength);
        }
        if (wordCount != null) {
            filters.put("word_count", wordCount);
        }
        if (containsCharacter != null) {
            filters.put("contains_character", containsCharacter);
        }

        return filters;
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