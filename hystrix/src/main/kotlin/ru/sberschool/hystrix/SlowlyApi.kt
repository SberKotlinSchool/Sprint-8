package ru.sberschool.hystrix

import feign.RequestLine
import ru.sberschool.hystrix.model.PokemonSpecie

interface SlowlyApi {

    @RequestLine("GET /pokemon-species/")
    fun getPokemonSpecie(): PokemonSpecie
}


