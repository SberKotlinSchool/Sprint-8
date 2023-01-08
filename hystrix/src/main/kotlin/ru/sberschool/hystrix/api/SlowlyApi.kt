package ru.sberschool.hystrix.api

import feign.RequestLine
import ru.sberschool.hystrix.model.PokeAPIResponse

interface SlowlyApi {
    @RequestLine("GET /")
    fun getAllMethods(): Map<String, String>

    @RequestLine("GET /item/")
    fun getItems(): PokeAPIResponse
}


