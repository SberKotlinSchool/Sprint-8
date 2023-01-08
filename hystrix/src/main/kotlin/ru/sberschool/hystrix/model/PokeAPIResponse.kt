package ru.sberschool.hystrix.model

data class PokeAPIResponse (
    val count: Long,
    val next: String? = null,
    val previous: String? = null,
    val results: List<ItemResponse>
)