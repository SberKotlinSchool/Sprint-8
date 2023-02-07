package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine
import ru.sberschool.entity.Person

interface SlowlyApi {

    @RequestLine("GET /people/{personId}/?format=json")
    fun getPerson(@Param(value = "personId") personId: Int): Person
}


