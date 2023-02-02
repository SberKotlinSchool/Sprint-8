package com.example.retailer.api.distributor

import org.hibernate.Hibernate
import java.io.Serializable
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
    @Column
    val name: String
): Serializable