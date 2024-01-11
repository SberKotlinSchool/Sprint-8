package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.LongDeserializer


data class SimpleResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String )
