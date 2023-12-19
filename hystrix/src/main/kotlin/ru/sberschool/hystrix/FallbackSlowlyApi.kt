package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getBerry(id: Int) = Berry(-1, "fallback berry")
}


