package com.example.stringAnalyzer.dto;

import com.example.stringAnalyzer.model.AnalyzedString;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StringResponse {

    private String id;
    private String value;
    private StringProperties properties;
   private LocalDateTime createdAt;
}