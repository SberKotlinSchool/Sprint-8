package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getPokemonByYellowColor() = Pokemon(104L, "UniversalYellowPokemon" )
}


