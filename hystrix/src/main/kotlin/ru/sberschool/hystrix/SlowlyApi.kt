package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /stat/{id}")
    fun getSomething(@Param("id") id:Long): SimpleResponse
}


