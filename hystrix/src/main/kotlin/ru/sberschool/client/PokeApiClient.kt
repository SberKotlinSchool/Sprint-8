package ru.sberschool.client

import feign.Param
import feign.RequestLine
import ru.sberschool.client.domain.Berry

interface PokeApiClient {
    @RequestLine("GET /berry/{id}")
    fun getBerry(@Param("id") id: Int): Berry
}
