package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Berry(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("growth_time") val growthTime: Int? = null,
    @JsonProperty("max_harvest") val maxHarvest: Int? = null
)
