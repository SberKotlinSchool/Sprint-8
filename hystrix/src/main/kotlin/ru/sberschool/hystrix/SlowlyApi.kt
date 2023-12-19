package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /berry/{id}")
    fun getBerry(@Param("id") id: Int): Berry
}


