package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class Berry(
    @JsonProperty("id")
    val id: Long? = null,
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("growth_time")
    val growthTime: Int? = null,
    @JsonProperty("max_harvest")
    val maxHarvest: Int? = null,
    @JsonProperty("natural_gift_power")
    val naturalGiftPower: Int? = null,
    @JsonProperty("size")
    val size: Int? = null,
    @JsonProperty("smoothness")
    val smoothness: Int? = null,
    @JsonProperty("soil_dryness")
    val soilDryness: Int? = null
)
