package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class ContestTypeResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("berry_flavor")
    val berryFlavour: BerryFlavour,
    @JsonProperty("names")
    val names: List<LocalizedName>
)

data class BerryFlavour(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("url")
    val url: String
)

data class LocalizedName(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("color")
    val color: String,
    @JsonProperty("language")
    val language: Language
)

data class Language(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("url")
    val url: String,
)
