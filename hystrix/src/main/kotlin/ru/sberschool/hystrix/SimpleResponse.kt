package ru.sberschool.hystrix

data class SimpleResponse(
    val id: Long = 0,
    val name: String = "Not Found",
    val base_experience: Int = 0,
    val height: Int = 0,
    val order: Int = 0,
    val weight: Int = 0,
)
