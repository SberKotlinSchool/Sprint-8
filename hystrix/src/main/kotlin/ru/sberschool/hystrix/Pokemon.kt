package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class Pokemon(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String
)