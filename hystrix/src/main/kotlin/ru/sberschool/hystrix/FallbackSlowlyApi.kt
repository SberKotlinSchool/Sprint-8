package ru.sberschool.hystrix

import feign.Param

class FallbackSlowlyApi : SlowlyApi{
    override fun findById(@Param("id") id: Long?) = Pokemon(1L, false, false, false, "ServerFallback")

}