package com.example.retailer.api.distributor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "items")
/**
 * Описание товара
 */
data class Item(
    @Id
    /**
     * Произвольный идентификатор
     */
    val id: Long,

    /**
     * Произвольное название
     */
    val name: String
)