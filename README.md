with the following scenarios:

    1. The 5 heaviest Pokémons.
    2. The 5 highest Pokémons.
    3. The 5 Pokémons with more base experience.

The source of the data is the PokéAPI
(https://pokeapi.co/api/v2/).
Must to have:
Create a Java/SpringBoot application
Test coverage of at least 90%
Nice to have:
Integration test
Production ready

I start it creating an antitie that represents Pokemon and necesary stats,
an i continue creating a service and controller to get information, 3 posibles https calls to consulting
- /heaviest
- /highest
- /more base exp

## Features
- Get top N heaviest Pokémon: `/heaviest/{limit}`
- Get top N tallest Pokémon: `/highest/{limit}`
- Get top N by experience: `/top-exp/{limit}`
- Optimized pagination and parallel processing

i create a PokemonResponse with the results list 

public class PokemonResponse 
    private Integer count;          // Total Pokémon available
    private String next;           // URL for next page (null if last page)
    private String previous;       // URL for previous page
    private List<PokemonResult> results; 

