package ru.sberschool.client.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Berry(
    val id: Int,
    val name: String,
    @JsonProperty("growth_time") val growthTime: Int,
    @JsonProperty("max_harvest") val maxHarvest: Int,
    @JsonProperty("natural_gift_power") val naturalGiftPower: Int,
    val size: Int,
    val smoothness: Int,
    @JsonProperty("soil_dryness") val soilDryness: Int
)