package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getCourses() = mapOf("Error" to "Service unavailable")
}


