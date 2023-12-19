package ru.sberschool.hystrix

import ru.sberschool.hystrix.dto.Pokemon

class FallbackPokemonApi : PokemonApi {

  override fun getPokemon(id: Int): Pokemon = Pokemon()

  override fun getPokemon(name: String): Pokemon = Pokemon()
}


