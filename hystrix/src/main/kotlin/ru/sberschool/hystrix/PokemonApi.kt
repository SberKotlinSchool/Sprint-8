package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine
import ru.sberschool.client.Pokemon

interface PokemonApi {
    @RequestLine("GET /pokemon/{id}")
    fun getPokemon(@Param("id") id: Long): Pokemon
}


