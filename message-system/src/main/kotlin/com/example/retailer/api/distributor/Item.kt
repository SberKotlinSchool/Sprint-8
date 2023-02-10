package com.example.retailer.api.distributor

import javax.persistence.Entity
import javax.persistence.Id
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Table

/**
 * Описание товара
 */
@Entity
@Table(name = "ITEMS")
data class Item(
    /**
     * Произвольный идентификатор
     */
    @Id
    @Column(name = "ID")
    val id: Long,

    /**
     * Произвольное название
     */
    @Column(name = "NAME")
    val name: String

) : Serializable
