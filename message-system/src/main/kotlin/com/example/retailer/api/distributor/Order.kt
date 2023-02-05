package com.example.retailer.api.distributor

import javax.persistence.*

/**
 * Описание заказа
 */
@Entity
@Table(name="orders")
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    @GeneratedValue
    val id: String?,

    /**
     * Произвольный адрес доставки
     */
    val address: String,

    /**
     * Произвольный получатель доставки
     */
    val recipient: String,

    /**
     * Список заказанных товаров
     */
    @OneToMany
    val items: List<Item>
)