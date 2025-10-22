package com.example.stringAnalyzer.controller;

import com.example.stringAnalyzer.dto.NaturalLanguageResponse;
import com.example.stringAnalyzer.dto.StringListResponse;
import com.example.stringAnalyzer.dto.StringRequest;
import com.example.stringAnalyzer.dto.StringResponse;
import com.example.stringAnalyzer.exception.InvalidQueryException;
import com.example.stringAnalyzer.exception.StringNotFoundException;
import com.example.stringAnalyzer.service.NaturalLanguageProcessor;
import com.example.stringAnalyzer.service.StringAnalyzerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "*")
@RestController
public class StringAnalyzerController {

    @Autowired
    StringAnalyzerService analyzerService;

    @Autowired
    NaturalLanguageProcessor naturalLanguageProcessor;

    public StringAnalyzerController(StringAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @PostMapping("/strings")
    public ResponseEntity<StringResponse> analyzeAndSaveString(@Valid @RequestBody StringRequest request) throws NoSuchAlgorithmException, MethodArgumentNotValidException {

        StringResponse response = analyzerService.createAndSave(request.getValue());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/strings/{string_value}")
    public ResponseEntity<StringResponse> getString(@PathVariable("string_value") String value) throws NoSuchAlgorithmException, StringNotFoundException {
        StringResponse response = analyzerService.findString(value);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/strings")
    public ResponseEntity<StringListResponse> getStringWithFilter(@RequestParam (required = false) Boolean is_palindrome,
                                                                  @RequestParam(required = false) Integer min_length,
                                                                  @RequestParam(required = false) Integer max_length,
                                                                  @RequestParam(required = false) Integer word_count,
                                                                  @RequestParam(required = false) String contains_character){

        StringListResponse response = analyzerService.getAllStrings(is_palindrome, min_length, max_length, word_count, contains_character);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/strings/filter-by-natural-language")
    public ResponseEntity<NaturalLanguageResponse> filterByNaturalLanguage(
            @RequestParam String query) throws InvalidQueryException {

        if (query == null || query.trim().isEmpty()) {
            throw new InvalidQueryException("Unable to parse natural language query");
        }

        NaturalLanguageResponse response = naturalLanguageProcessor.processNaturalLanguageQuery(query);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/strings/{string_value}")
    public ResponseEntity<Void> deleteString(
            @PathVariable("string_value") String stringValue) {

        analyzerService.deleteString(stringValue);

        return ResponseEntity.noContent().build();
    }
}