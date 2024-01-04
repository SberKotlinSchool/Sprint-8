package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getSomething(id: Long) = SimpleResponse()
}


