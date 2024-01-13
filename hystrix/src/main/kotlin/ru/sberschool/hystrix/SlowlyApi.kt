package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /pokemon/{name}")
    fun getPokemon(@Param ("name") name: String): Pokemon
}


