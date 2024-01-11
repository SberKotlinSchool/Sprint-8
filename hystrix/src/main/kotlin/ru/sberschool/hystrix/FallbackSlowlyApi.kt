package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getSomething( name: String ) = SimpleResponse( id = -1, name = name)
}


