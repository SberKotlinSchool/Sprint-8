package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface BerriesApi {
    @RequestLine("GET /api/v2/berry/{id}")
    fun getBerry(@Param("id") id: Long): Berry
}


