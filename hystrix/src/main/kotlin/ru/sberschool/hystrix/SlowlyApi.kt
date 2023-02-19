package ru.sberschool.hystrix

import feign.RequestLine

interface SlowlyApi {

    @RequestLine("GET /pokemon/25")
    fun getPokemon(): Pokemon
}


