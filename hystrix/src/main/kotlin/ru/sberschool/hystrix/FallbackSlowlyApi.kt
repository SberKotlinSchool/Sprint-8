package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getPokemon(id: Long): Pokemon = Pokemon(-1, "NONE")
}


