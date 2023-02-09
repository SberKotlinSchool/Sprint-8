package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class PokeResponse(
    @JsonProperty("status")
    val status: String,
    @JsonProperty("message")
    val message: String,
    @JsonProperty("timestamp")
    val timestamp: String
)


