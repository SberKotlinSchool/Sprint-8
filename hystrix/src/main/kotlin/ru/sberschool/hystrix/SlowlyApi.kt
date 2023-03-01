package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /pokemon/{id}")
    fun getPokemon(@Param("id") id: Long): Pokemon
}


