package ru.sberschool.hystrix

import feign.RequestLine

interface PokemonApi {
    @RequestLine("GET /pokemon/pikachu")
    fun getPickachuInfo(): PikachuInfoResponse
}



