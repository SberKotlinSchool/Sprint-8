package ru.sberschool.hystrix

import feign.RequestLine
import pokemon.Pokemon

interface SlowlyApi {
    @RequestLine("GET /pokemon/35")
    fun getPokemon(): Pokemon
}


