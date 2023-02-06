package ru.sberschool.hystrix

class FallbackPokeApi : PokeApi {
    override fun getBerry(id: Int): Berry = Berry(20, "raspberries", 1, 1, 1,
        1, 1, 1)
}