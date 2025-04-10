package com.alea.pokemon.test.models;

import java.util.List;
import lombok.Data;

@Data
public class PokemonResponse {
    private Integer count;
    private String next;
    private String previous;
    private List<PokemonResult> results;
}
// Move this class to a new file named PokemonResult.java