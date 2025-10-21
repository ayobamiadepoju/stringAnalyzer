package com.example.stringAnalyzer.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringRequest {

    @NotNull(message = "Value field is required")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    private String value;
}
