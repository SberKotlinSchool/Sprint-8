package com.example.retailer.api.distributor

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
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
    @GeneratedValue
    val id: Long,

    /**
     * Произвольное название
     */
    @Column(name = "item_name")
    val name: String
)