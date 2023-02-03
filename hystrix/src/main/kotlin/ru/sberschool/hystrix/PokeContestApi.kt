package ru.sberschool.hystrix

import feign.RequestLine

interface PokeContestApi {

    @RequestLine("GET /contest-type/cool")
    fun getCoolContestType(): ContestTypeResponse
}
