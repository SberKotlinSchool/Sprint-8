package ru.sberschool.hystrix

import ru.sberschool.hystrix.model.PokemonSpecie
import ru.sberschool.hystrix.model.PokemonSpecieResult

class FallbackSlowlyApi : SlowlyApi {

     val TEST_URL = "http://test"

    override fun getPokemonSpecie(): PokemonSpecie {
        val pokemonResult = PokemonSpecieResult("test", TEST_URL)
        return PokemonSpecie(1, TEST_URL, TEST_URL, listOf(pokemonResult))
    }
}


