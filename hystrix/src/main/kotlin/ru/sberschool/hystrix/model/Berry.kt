package ru.sberschool.hystrix.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Berry (
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("natural_gift_power")
    val power: Int,
    @JsonProperty("smoothness")
    val smoothness: Int
 ) {
}