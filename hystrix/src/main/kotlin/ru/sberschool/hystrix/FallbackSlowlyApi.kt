package ru.sberschool.hystrix

const val NEXT_URL = "https://pokeapi.co/api/v2/pokemon?offset=3&limit=3"
const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon"

class FallbackSlowlyApi : SlowlyApi {

    /**
     * Заглушка ответа /pokemon при недоступности API.
     */
    override fun getPokemonPage() =
            PokemonPage(3, NEXT_URL, null,
                    listOf(
                            Pokemon("pokemon1", "$POKEMON_URL/1/"),
                            Pokemon("pokemon2", "$POKEMON_URL/2/"),
                            Pokemon("pokemon3", "$POKEMON_URL/3/")
                    )
            )
}


