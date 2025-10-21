package com.example.stringAnalyzer.storage;

import com.example.stringAnalyzer.dto.StringListResponse;
import com.example.stringAnalyzer.model.AnalyzedString;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class StringStorage{

    private final Map<String, AnalyzedString> storage = new ConcurrentHashMap<>();

    public void save(AnalyzedString analyzedString) {
        storage.put(analyzedString.getValue(), analyzedString);
    }

    public boolean existByValue(String value){
        return storage.containsKey(value);
    }

    public Optional<AnalyzedString> findByValue(String value) {
        return Optional.ofNullable(storage.get(value));
    }

    public List<AnalyzedString> findByFilters(Boolean isPalindrome, Integer minLength, Integer maxLength, Integer wordCount){
        return storage.values().stream()
                .filter(s -> isPalindrome == null || s.getIsPalindrome().equals(isPalindrome))
                .filter(s -> minLength == null || s.getLength() >= minLength)
                .filter(s -> maxLength == null || s.getLength() <= maxLength)
                .filter(s -> wordCount == null || s.getWordCount().equals(wordCount))
                .collect(Collectors.toList());
    }

    public boolean deleteByValue(String value) {
        AnalyzedString analyzedString = storage.remove(value);

        if (analyzedString != null) {
            storage.remove(analyzedString.getValue());
            return true;
        }
        return false;
    }

    public int count() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }
}