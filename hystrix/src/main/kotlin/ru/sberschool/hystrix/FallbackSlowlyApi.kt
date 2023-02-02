package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getCharmander(): CharmanderApi = CharmanderApi(id=-1, name = "predefined data")
}


