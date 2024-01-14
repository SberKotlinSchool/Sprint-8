package com.example.retailer.api.distributor

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator

/**
 * Описание заказа
 */
@Entity
@Table(name = "orders")
@kotlinx.serialization.Serializable
data class Order(
        /**
         * Уникальный идентификатор заказа на стороне ретейлера
         */
        @Id
        @GeneratedValue(generator = "system-uuid")
        @GenericGenerator(name = "system-uuid", strategy = "uuid")
        val id: String? = null,

        /**
         * Произвольный адрес доставки
         */
        @Column val address: String,

        /**
         * Произвольный получатель доставки
         */
        @Column val recipient: String,

        /**
         * Список заказанных товаров
         */
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "order_id")
        val items: List<Item> = mutableListOf()
)