package ru.sberschool.hystrix

class FallbackPokeApi : PokeApi {
    override fun getSomething(id: Int): PokeResponse {
        return PokeResponse("fallback data status", "fallback data message", "fallback data timestamp")
    }
}