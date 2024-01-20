package com.example.retailer.api.distributor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * Описание товара
 */
@Entity
data class Item(
    /**
     * Произвольный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    /**
     * Произвольное название
     */
    val name: String,

    @ManyToOne(targetEntity = Order::class)
    val order: Order? = null
)