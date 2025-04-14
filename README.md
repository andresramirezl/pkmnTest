with the following scenarios:

    1. The 5 heaviest Pokémons.
    2. The 5 highest Pokémons.
    3. The 5 Pokémons with more base experience.

# POKEMON API

API REST develop with Spring Boot Get info from API POKEMON.
The source of the data is the PokéAPI
(https://pokeapi.co/api/v2/).

## Tecnologies Utilized

- Java 21
- Spring Boot 3.4.3
- Gradle
- JUnit 5
- Swagger/OpenAPI
- Lombok

## Features I
I start it creating an antitie that represents Pokemon and necesary stats,
an i continue creating a service and controller to get information, 3 posibles https calls to consulting
- /heaviest
- /highest
- /more base exp

## Features II
- Get top N heaviest Pokémon: `/heaviest/{limit}`
- Get top N tallest Pokémon: `/highest/{limit}`
- Get top N by experience: `/top-exp/{limit}`
- Optimized pagination and parallel processing


## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:

```bash
git clone https://github.com/andresramirezl/pkmnTest.git
cd pkmnTest
```

2. Run the application:

```bash
./gradlew bootrun
```

3. Access the API documentation:

```bash
http://localhost:8080/swagger-ui/index.html
```

create a PokemonResponse with the results list 

public class PokemonResponse 
    private Integer count;          // Total Pokémon available
    private String next;           // URL for next page (null if last page)
    private String previous;       // URL for previous page
    private List<PokemonResult> results; 

Create Integration and Tests for controllers
