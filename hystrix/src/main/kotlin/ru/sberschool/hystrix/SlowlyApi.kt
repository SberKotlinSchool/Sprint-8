package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /{id}")
    fun findById(@Param("id") id: Long?): Pokemon?
}


