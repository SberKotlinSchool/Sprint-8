package ru.sberschool.hystrix.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonSpecieResult(
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("url")
    val url: String? = null
)