package ru.sberschool.hystrix.pokeapi

import feign.RequestLine

interface PokeApi {
    @RequestLine("GET /api/v2/ability/battle-armor")
    fun getAbilityBattleArmor(): Any
}