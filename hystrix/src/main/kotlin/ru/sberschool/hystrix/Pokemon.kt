package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class Pokemon(
    @JsonProperty("id") val id: Long?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("base_experience") val baseExperience: Int? = null,
    @JsonProperty("height") val height: Int? = null,
    @JsonProperty("weight") val weight: Int? = null
)