package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

data class Berry(@JsonProperty("id") val id: Int,
                 @JsonProperty("name") val name: String,
                 @JsonProperty("growth_time") val growthTime: Int,
                 @JsonProperty("max_harvest") val maxHarvest: Int,
                 @JsonProperty("natural_gift_power") val naturalGiftPower: Int,
                 @JsonProperty("size") val size: Int,
                 @JsonProperty("smoothness") val smoothness: Int,
                 @JsonProperty("soil_dryness") val soilDryness: Int
)
