package com.example.retailer.api.distributor

import javax.persistence.*

/**
 * Уведомление об изменении заказа
 */
@Entity
data class OrderInfo(

    /**
     * Произвольный идентификатор
     */
    @Id
    @GeneratedValue
    var id: String = "",

    /**
     * Уникальный идентификатор заказа
     *
     * @see com.example.retailer.api.distributor.Item#id
     */
    @JoinColumn(name = "order_id")
    @OneToOne
    val order: Order,

    /**
     * Статус заказа:
     *  Created
     *
     */
    @Enumerated(value = EnumType.STRING)
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    val signature: String,
)