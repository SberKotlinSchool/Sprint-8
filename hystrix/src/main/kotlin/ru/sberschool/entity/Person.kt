package ru.sberschool.entity

import com.fasterxml.jackson.annotation.JsonProperty

class Person (

    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("height")
    val height: String? = null,

    @JsonProperty("birth_year")
    val birthYear: String? = null,

    @JsonProperty("gender")
    val gender: String? = null)
{}