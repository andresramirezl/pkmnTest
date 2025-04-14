package com.alea.pokemon.test.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.alea.pokemon.test.models.Pokemon;

@SpringBootTest
@AutoConfigureMockMvc
class PokemonResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getHeaviest_ShouldReturnFivePokemons() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/pokemon/heaviest")
                .param("batchSize", "10"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<Pokemon> pokemons = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<List<Pokemon>>() {}
        );
        
        assertEquals(5, pokemons.size());
        assertTrue(isWeightOrdered(pokemons));
    }

    @Test
    void getHighest_ShouldReturnFivePokemons() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/pokemon/highest")
                .param("batchSize", "10"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<Pokemon> pokemons = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<List<Pokemon>>() {}
        );
        
        assertEquals(5, pokemons.size());
        assertTrue(isHeightOrdered(pokemons));
    }

    @Test
    void getMostExperienced_ShouldReturnFivePokemons() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(get("/pokemon/most-experienced")
                .param("batchSize", "10"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<Pokemon> pokemons = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<List<Pokemon>>() {}
        );
        
        assertEquals(5, pokemons.size());
        assertTrue(isExperienceOrdered(pokemons));
    }

    private boolean isWeightOrdered(List<Pokemon> pokemons) {
        for (int i = 0; i < pokemons.size() - 1; i++) {
            if (pokemons.get(i).getWeight() < pokemons.get(i + 1).getWeight()) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeightOrdered(List<Pokemon> pokemons) {
        for (int i = 0; i < pokemons.size() - 1; i++) {
            if (pokemons.get(i).getHeight() < pokemons.get(i + 1).getHeight()) {
                return false;
            }
        }
        return true;
    }

    private boolean isExperienceOrdered(List<Pokemon> pokemons) {
        for (int i = 0; i < pokemons.size() - 1; i++) {
            if (pokemons.get(i).getBaseExperience() < pokemons.get(i + 1).getBaseExperience()) {
                return false;
            }
        }
        return true;
    }
} 