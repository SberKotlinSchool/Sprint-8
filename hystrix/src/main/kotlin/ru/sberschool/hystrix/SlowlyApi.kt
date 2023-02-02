package ru.sberschool.hystrix

import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /pokemon/charmander")
    fun getCharmander(): CharmanderApi
}


