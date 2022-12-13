package ru.sberschool.hystrix

import feign.RequestLine

interface SlowlyApi {
    @RequestLine("GET /daily_json.js")
    fun getCourses(): Any
}


