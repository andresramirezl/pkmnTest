package com.alea.pokemon.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Pokemon {
    private Long id;
    private String name;
    private Integer weight;
    private Integer height;
    
    @JsonProperty("base_experience")
    private Integer baseExperience;
} 