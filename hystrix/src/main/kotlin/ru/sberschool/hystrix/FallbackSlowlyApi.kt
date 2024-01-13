package ru.sberschool.hystrix

import feign.Param

class FallbackSlowlyApi : SlowlyApi {
    override fun getPokemon(name: String): Pokemon = Pokemon(-1, "error")
}


