package com.alea.pokemon.test.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alea.pokemon.test.models.Pokemon;
import com.alea.pokemon.test.models.PokemonResponse;
import com.alea.pokemon.test.models.PokemonResult;

@SpringBootTest
class PokemonResourceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonResource pokemonResource;

    private Pokemon pokemon1, pokemon2, pokemon3;
    private PokemonResponse mockResponse;

    @BeforeEach
    void setUp() {
        // Setup test data
        pokemon1 = new Pokemon();
        pokemon1.setName("Snorlax");
        pokemon1.setWeight(4600);
        pokemon1.setHeight(21);
        pokemon1.setBaseExperience(189);

        pokemon2 = new Pokemon();
        pokemon2.setName("Wailord");
        pokemon2.setWeight(3980);
        pokemon2.setHeight(145);
        pokemon2.setBaseExperience(175);

        pokemon3 = new Pokemon();
        pokemon3.setName("Pikachu");
        pokemon3.setWeight(60);
        pokemon3.setHeight(4);
        pokemon3.setBaseExperience(112);

        // Setup mock response
        mockResponse = new PokemonResponse();
        mockResponse.setResults(Arrays.asList(
            createPokemonResult("Snorlax", "https://pokeapi.co/api/v2/pokemon/143"),
            createPokemonResult("Wailord", "https://pokeapi.co/api/v2/pokemon/321"),
            createPokemonResult("Pikachu", "https://pokeapi.co/api/v2/pokemon/25")
        ));
        mockResponse.setNext(null);
    }

    private PokemonResult createPokemonResult(String name, String url) {
        PokemonResult result = new PokemonResult();
        result.setName(name);
        result.setUrl(url);
        return result;
    }

    @Test
    void getHeaviest_ShouldReturnHeaviestPokemons() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(PokemonResponse.class)))
            .thenReturn(mockResponse);
        when(restTemplate.getForObject(contains("/143"), eq(Pokemon.class)))
            .thenReturn(pokemon1);
        when(restTemplate.getForObject(contains("/321"), eq(Pokemon.class)))
            .thenReturn(pokemon2);
        when(restTemplate.getForObject(contains("/25"), eq(Pokemon.class)))
            .thenReturn(pokemon3);

        // Act
        ResponseEntity<List<Pokemon>> response = pokemonResource.getHeaviest(3);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals("Snorlax", response.getBody().get(0).getName());
        assertEquals("Wailord", response.getBody().get(1).getName());
    }

    @Test
    void getHighest_ShouldReturnHighestPokemons() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(PokemonResponse.class)))
            .thenReturn(mockResponse);
        when(restTemplate.getForObject(contains("/143"), eq(Pokemon.class)))
            .thenReturn(pokemon1);
        when(restTemplate.getForObject(contains("/321"), eq(Pokemon.class)))
            .thenReturn(pokemon2);
        when(restTemplate.getForObject(contains("/25"), eq(Pokemon.class)))
            .thenReturn(pokemon3);

        // Act
        ResponseEntity<List<Pokemon>> response = pokemonResource.getHighest(3);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals("Wailord", response.getBody().get(0).getName());
        assertEquals("Snorlax", response.getBody().get(1).getName());
    }

    @Test
    void getMostExperienced_ShouldReturnMostExperiencedPokemons() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(PokemonResponse.class)))
            .thenReturn(mockResponse);
        when(restTemplate.getForObject(contains("/143"), eq(Pokemon.class)))
            .thenReturn(pokemon1);
        when(restTemplate.getForObject(contains("/321"), eq(Pokemon.class)))
            .thenReturn(pokemon2);
        when(restTemplate.getForObject(contains("/25"), eq(Pokemon.class)))
            .thenReturn(pokemon3);

        // Act
        ResponseEntity<List<Pokemon>> response = pokemonResource.getMostExperienced(3);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals("Snorlax", response.getBody().get(0).getName());
        assertEquals("Wailord", response.getBody().get(1).getName());
    }
} 