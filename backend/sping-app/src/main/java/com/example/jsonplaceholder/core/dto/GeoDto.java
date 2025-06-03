package com.example.jsonplaceholder.core.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoDto {
    private Long id;
    @NotBlank
    private String lat;
    @NotBlank
    private String lng;
} 