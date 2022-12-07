package ru.sberschool.hystrix.pokeapi

class FallbackPokeApi : PokeApi {
    override fun getAbilityBattleArmor(): Any {
        return mapOf("effect_changes" to "error")
    }
}