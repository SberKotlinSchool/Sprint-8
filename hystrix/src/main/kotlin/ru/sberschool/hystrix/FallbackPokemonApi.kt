package ru.sberschool.hystrix

import ru.sberschool.client.Pokemon

class FallbackPokemonApi : PokemonApi {
    override fun getPokemon(id: Long) = Pokemon(0, "NOT_FOUND")
}


