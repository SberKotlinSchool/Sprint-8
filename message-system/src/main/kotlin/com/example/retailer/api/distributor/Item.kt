package com.example.retailer.api.distributor

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Описание товара
 */
@Entity
@Table(name = "items")
@kotlinx.serialization.Serializable
data class Item(
    @Id val id: Long,
    @Column val name: String
)
