package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /pokemon/{id}")
    fun getSomething(@Param("id") id: Long): SimpleResponse
}


