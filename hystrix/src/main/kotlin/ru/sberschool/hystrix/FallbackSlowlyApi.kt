package ru.sberschool.hystrix

import model.Ability
import model.AbilityPage

class FallbackSlowlyApi : SlowlyApi {

    override fun getAbilityPage(): AbilityPage {
        val ability = Ability("stench", "http://test/ability/1")
        return AbilityPage(1, "http://test/ability?offset=20&limit=20", null, listOf(ability))
    }
}


