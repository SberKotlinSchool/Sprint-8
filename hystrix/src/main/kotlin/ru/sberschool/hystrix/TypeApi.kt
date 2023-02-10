package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine

interface TypeApi {
    @RequestLine("GET /type/{typeId}")
    fun getTypeById(@Param(value = "typeId") typeId: Int) : Type
}


