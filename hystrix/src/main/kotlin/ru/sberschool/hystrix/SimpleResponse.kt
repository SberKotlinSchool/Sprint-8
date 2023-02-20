package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class SimpleResponse(
    @JsonProperty("game_index")
    val gameIndex: Long,
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("is_battle_only")
    val isBattleOnly: Boolean,
    @JsonProperty("name")
    val name: String
    )
