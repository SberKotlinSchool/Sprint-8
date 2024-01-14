package ru.sberschool.hystrix.dto

data class Pokemon(
        val id: Int = 35,
        val name: String = "clefairy",
        val baseExperience: Int = 113,
        val height: Int = 6,
        val isDefault: Boolean = true,
        val order: Int = 56,
        val weight: Int = 75,
)
