package ru.sberschool.hystrix

import feign.QueryMap
import feign.RequestLine

interface PokeApi {
    @RequestLine("GET /api/v2/berry/")
    fun getBerries(@QueryMap params: BerryRequestParams): BerryPageableResponse
}

class PokeApiFallback : PokeApi {
    override fun getBerries(params: BerryRequestParams) = BerryPageableResponse(
        10,
        "nextPage",
        null,
        listOf(BerryResult("someBerry", "https://pokeapi.site/urlToBerry"))
    )
}