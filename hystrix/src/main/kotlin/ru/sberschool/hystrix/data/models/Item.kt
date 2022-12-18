package ru.sberschool.hystrix.data.models

import com.fasterxml.jackson.annotation.JsonProperty


data class Item(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("cost")
    val cost: Int
)



