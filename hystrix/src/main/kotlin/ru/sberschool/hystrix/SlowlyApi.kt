package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty
import feign.RequestLine


data class Pokemon( @JsonProperty("id") var id: Long, @JsonProperty("name") var name: String)

interface SlowlyApi {
    @RequestLine("GET /pokemon-color/yellow")
    fun getPokemonByYellowColor(): Pokemon
}


