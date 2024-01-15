package ru.sberschool.hystrix

import ru.sberschool.entity.PokemonRs

class FallbackPokemonApi : PokemonApi {
    override fun getPokemon(id: Long): PokemonRs = PokemonRs(-1, "UNDEFINED")
}

