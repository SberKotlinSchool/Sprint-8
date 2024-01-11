package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /api/v2/pokemon/{name}")
    fun getSomething( @Param("name") name: String ): SimpleResponse
}


