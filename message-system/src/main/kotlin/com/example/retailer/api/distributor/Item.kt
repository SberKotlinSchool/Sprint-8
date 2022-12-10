package com.example.retailer.api.distributor

import javax.persistence.*

/**
 * Описание товара
 */
@Entity
data class Item(
    /**
     * Произвольный идентификатор
     */
    @Id
    val id: Long,

    /**
     * Произвольное название
     */
    @Column(nullable = false)
    val name: String
)