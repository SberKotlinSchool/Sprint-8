package ru.sberschool.hystrix

class FallbackPokemonApi : PokemonApi {
    override fun getPickachuInfo() = PikachuInfoResponse(-1)
}
