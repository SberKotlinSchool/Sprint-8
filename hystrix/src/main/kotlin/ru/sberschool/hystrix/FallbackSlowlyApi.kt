package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getDitto(): Ditto  = Ditto(-1, -1, "predefined data")
}


