package com.example.retailer.api.distributor

import javax.persistence.Entity
import javax.persistence.Id

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
    val name: String
)