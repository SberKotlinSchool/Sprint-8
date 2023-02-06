package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface PokeApi {

    @RequestLine("GET /berry/{id}")
    fun getBerry(@Param("id") id: Int): Berry

}