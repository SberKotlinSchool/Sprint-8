package ru.sberschool.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonRs (
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("name")
    val name: String?,

    @JsonProperty("order")
    val order: Int? = null,

    @JsonProperty("base_experience")
    val baseExperience: Int? = null,

    @JsonProperty("height")
    val height: Int? = null,

    @JsonProperty("weight")
    val weight: Int? = null
)