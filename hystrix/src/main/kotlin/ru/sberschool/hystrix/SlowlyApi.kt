package ru.sberschool.hystrix

import feign.RequestLine
import model.Ability
import model.AbilityPage

interface SlowlyApi {
    @RequestLine("GET /ability")
    fun getAbilityPage(): AbilityPage
}


