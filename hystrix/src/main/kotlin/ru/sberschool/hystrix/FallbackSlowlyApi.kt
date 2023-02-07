package ru.sberschool.hystrix

const val NEXT_URL = "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20"
const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon"

class FallbackSlowlyApi : SlowlyApi {

    override fun getPokemonPage() =
        PokemonPage(3, NEXT_URL, null,
            listOf(
                Pokemon("bulbasaur", "$POKEMON_URL/1/"),
                Pokemon("ivysaur", "$POKEMON_URL/2/"),
                Pokemon("venusaur", "$POKEMON_URL/3/")
            )
        )
}


