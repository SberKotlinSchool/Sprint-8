package ru.sberschool.hystrix.dto

import com.fasterxml.jackson.annotation.JsonProperty

class Item(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("cost")
    val cost: Int
)
