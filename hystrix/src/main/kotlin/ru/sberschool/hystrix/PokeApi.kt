package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface PokeApi {
    @RequestLine("GET /v2/divisions/{id}")
    fun getSomething(@Param(value = "id") id: Int): PokeResponse
}