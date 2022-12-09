package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class Ditto(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("order")
    val order: Long,
    @JsonProperty("name")
    val name: String
)