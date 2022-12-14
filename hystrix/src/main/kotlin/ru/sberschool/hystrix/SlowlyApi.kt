package ru.sberschool.hystrix

import feign.RequestLine
import ru.sberschool.hystrix.dto.Pokemon

interface SlowlyApi {
    @RequestLine("GET /pokemon/35")
    fun getPokemon(): Pokemon
}