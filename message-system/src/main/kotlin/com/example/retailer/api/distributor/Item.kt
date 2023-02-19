package com.example.retailer.api.distributor

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Описание товара
 */
@Entity
@Table(name = "item", schema = "rabbit")
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
): Serializable