package ru.sberschool.hystrix

import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /pokemon")
    fun getPokemonPage(): PokemonPage
}
