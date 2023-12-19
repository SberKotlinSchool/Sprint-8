package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine
import ru.sberschool.hystrix.dto.Pokemon

interface PokemonApi {
    @RequestLine("GET /pokemon/{id}")
    fun getPokemon(@Param(value = "id") id: Int): Pokemon

    @RequestLine("GET /pokemon/{name}")
    fun getPokemon(@Param(value = "name") name: String): Pokemon
}
