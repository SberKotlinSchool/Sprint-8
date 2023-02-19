package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getPokemon() = Pokemon(-1, "Timeout expired")
}


