package com.example.retailer.api.distributor

import javax.persistence.*

/**
 * Описание заказа
 */
@Entity
@Table(name ="Order1")
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    @GeneratedValue
    var id: String = "",

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
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "order_info_link",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")])
    val items: List<Item>
)