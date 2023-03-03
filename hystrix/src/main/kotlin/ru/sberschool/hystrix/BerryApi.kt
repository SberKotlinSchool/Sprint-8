package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine
import ru.sberschool.hystrix.model.Berry

interface BerryApi {

    @RequestLine("GET /berry/{berryId}")
    fun getBerry(@Param(value = "berryId") berryId: Int) : Berry
}