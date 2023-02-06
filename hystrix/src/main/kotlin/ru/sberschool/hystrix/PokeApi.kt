package ru.sberschool.hystrix

import feign.QueryMap
import feign.RequestLine

interface PokeApi {
    @RequestLine("GET /api/v2/berry/")
    fun getBerries(@QueryMap params: BerryRequestParams): BerryPageableResponse


}
