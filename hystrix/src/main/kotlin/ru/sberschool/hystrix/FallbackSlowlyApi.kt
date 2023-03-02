package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getUser() = User("DIvan")
}


