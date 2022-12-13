package ru.sberschool.hystrix

import ru.sberschool.hystrix.dto.Pokemon

class FallbackSlowlyApi : SlowlyApi {
    override fun getPokemon() = Pokemon(-1, "Too long to wait")
}