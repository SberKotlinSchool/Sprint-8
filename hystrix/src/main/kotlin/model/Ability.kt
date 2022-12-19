package model

import com.fasterxml.jackson.annotation.JsonProperty

data class Ability(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("url")
    val url: String? = null
)
