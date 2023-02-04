package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getSomething(id:Long) = SimpleResponse(1,1,false,"predefined data")
}


