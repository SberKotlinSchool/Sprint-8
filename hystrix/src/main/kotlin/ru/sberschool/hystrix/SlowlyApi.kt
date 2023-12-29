package ru.sberschool.hystrix

import feign.RequestLine

/**
 * Интерфейс, который будет обрабатывать Hystrix.
 */
interface SlowlyApi {
    @RequestLine("GET /pokemon")
    fun getPokemonPage(): PokemonPage
}


