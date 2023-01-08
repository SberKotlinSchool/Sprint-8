package ru.sberschool.hystrix.api

import ru.sberschool.hystrix.model.PokeAPIResponse

class FallbackSlowlyApi : SlowlyApi {
    override fun getAllMethods() = mapOf("" to "")
    override fun getItems(): PokeAPIResponse = PokeAPIResponse(-1, null, null, emptyList())
}


