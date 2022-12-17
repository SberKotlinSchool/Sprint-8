package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty


data class PikachuInfoResponse(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("base_experience")
    val baseExperience: Int? = null,
    @JsonProperty("height")
    val height: Int? = null,
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("weight")
    val weight: Int? = null,
)

