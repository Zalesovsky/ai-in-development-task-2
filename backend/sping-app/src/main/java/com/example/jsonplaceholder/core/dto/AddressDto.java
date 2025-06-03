package com.example.jsonplaceholder.core.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    @NotBlank
    private String street;
    @NotBlank
    private String suite;
    @NotBlank
    private String city;
    @NotBlank
    private String zipcode;
    private GeoDto geo;
} 