package com.example.retailer.api.distributor

import java.io.Serializable
import javax.persistence.*

/**
 * Уведомление об изменении заказа
 */
@Entity
@Table(name = "order_info", schema = "rabbit")
data class OrderInfo(

    /**
     * Уникальный идентификатор заказа
     * @see com.example.retailer.api.distributor.Item#id
     */
    @Id
    val orderId: String,

    /**
     * Статус заказа:
     *  Created
     *
     */
    @Column
    @Enumerated(EnumType.STRING)
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    @Column
    val signature: String,
): Serializable