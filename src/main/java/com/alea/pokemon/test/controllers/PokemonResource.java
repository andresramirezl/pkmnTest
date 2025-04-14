package com.alea.pokemon.test.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alea.pokemon.test.models.Pokemon;
import com.alea.pokemon.test.models.PokemonResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController 
@RequestMapping("/pokemon")
@Tag(name = "pokemon", description = "API to manage POKEMON")
public class PokemonResource {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PokemonResource.class);
    /* 
    1. The 5 heaviest Pokémons.
    2. The 5 highest Pokémons.
    3. The 5 Pokémons with more base experience.
     */

     @Operation(summary = "The 5 heaviest Pokémons.")
     @GetMapping("/heaviest")
     public ResponseEntity<List<Pokemon>> getHeaviest(@RequestParam(required = false, defaultValue = "1000") Integer batchSize) {
         try {
      
             List<Pokemon> allPokemons = new ArrayList<>();
              String nextUrl = String.format("https://pokeapi.co/api/v2/pokemon?limit=%d", batchSize); // fget url
            
             logger.info(nextUrl);
             // 1. get pagination pkmn
             while (nextUrl != null && allPokemons.size() < 5000) { // security limit
                 PokemonResponse response = restTemplate.getForObject(nextUrl, PokemonResponse.class);
                 if (response == null || response.getResults() == null) break;
                 
                 // Improve rendiment
                 List<Pokemon> batch = response.getResults().parallelStream()
                         .map(result -> restTemplate.getForObject(result.getUrl(), Pokemon.class))
                         .filter(Objects::nonNull)
                         .collect(Collectors.toList());
                 
                 allPokemons.addAll(batch);
                 nextUrl = response.getNext(); 
             }
     
             // 2. Order and get
             List<Pokemon> heaviest = allPokemons.stream()
                     .sorted(Comparator.comparingInt(Pokemon::getWeight).reversed())
                     .limit(5)
                     .collect(Collectors.toList());
     
             return ResponseEntity.ok(heaviest);
             
         } catch (RestClientException e) {
             return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
         }
     }

    @Operation(summary = "The 5 highest Pokémons.")
    @GetMapping("/highest")
    public ResponseEntity<List<Pokemon>> getHighest(@RequestParam(required = false, defaultValue = "1000") Integer batchSize) {
        try {
            List<Pokemon> allPokemons = new ArrayList<>();
            String nextUrl = String.format("https://pokeapi.co/api/v2/pokemon?limit=%d", batchSize);
            
            logger.info(nextUrl);
            while (nextUrl != null && allPokemons.size() < 5000) {
                PokemonResponse response = restTemplate.getForObject(nextUrl, PokemonResponse.class);
                if (response == null || response.getResults() == null) break;
                
                List<Pokemon> batch = response.getResults().parallelStream()
                        .map(result -> restTemplate.getForObject(result.getUrl(), Pokemon.class))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                
                allPokemons.addAll(batch);
                nextUrl = response.getNext();
            }

            List<Pokemon> highest = allPokemons.stream()
                    .sorted(Comparator.comparingInt(Pokemon::getHeight).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(highest);
            
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @Operation(summary = "The 5 Pokémons with more base experience.")
    @GetMapping("/most-experienced")
    public ResponseEntity<List<Pokemon>> getMostExperienced(@RequestParam(required = false, defaultValue = "1000") Integer batchSize) {
        try {
            List<Pokemon> allPokemons = new ArrayList<>();
            String nextUrl = String.format("https://pokeapi.co/api/v2/pokemon?limit=%d", batchSize);
            
            logger.info(nextUrl);
            while (nextUrl != null && allPokemons.size() < 5000) {
                PokemonResponse response = restTemplate.getForObject(nextUrl, PokemonResponse.class);
                if (response == null || response.getResults() == null) break;
                
                List<Pokemon> batch = response.getResults().parallelStream()
                        .map(result -> restTemplate.getForObject(result.getUrl(), Pokemon.class))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                
                allPokemons.addAll(batch);
                nextUrl = response.getNext();
            }

            List<Pokemon> mostExperienced = allPokemons.stream()
                    .sorted(Comparator.comparingInt(Pokemon::getBaseExperience).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(mostExperienced);
            
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

}
