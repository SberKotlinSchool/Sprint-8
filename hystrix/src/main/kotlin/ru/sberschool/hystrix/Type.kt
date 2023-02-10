package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.node.ArrayNode

/**
 * Тип покемона: летающий
 */
data class Type(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("pokemon")
    @JsonDeserialize(using = StringListJsonDeserializer::class)
    val pokemons: List<String>,

    @JsonProperty("moves")
    @JsonDeserialize(using = StringListJsonDeserializer::class)
    val moves: List<String>


)

class StringListJsonDeserializer : JsonDeserializer<List<String>>() {
    override fun deserialize(jp: JsonParser?, p1: DeserializationContext?): List<String> {
        val arrayNode = jp?.codec?.readTree<ArrayNode>(jp);
        return arrayNode?.map { it.toString() }?.toList()?: emptyList()
    }
}
