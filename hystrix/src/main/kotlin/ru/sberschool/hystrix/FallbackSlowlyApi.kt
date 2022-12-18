package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getDitto() : PokemonDitto = PokemonDitto(-1,-1,"pokemon", -1)
}


