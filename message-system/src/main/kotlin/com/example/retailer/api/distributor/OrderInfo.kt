package com.example.retailer.api.distributor

import java.io.Serializable
import javax.persistence.*

/**
 * Уведомление об изменении заказа
 */
@Entity
@Table(name = "ORDER_INFO")
data class OrderInfo(

    /**
     * Уникальный идентификатор заказа
     *
     * @see com.example.retailer.api.distributor.Item#id
     */
    @Id
    @Column(name = "ORDER_ID")
    val orderId: String,

    /**
     * Статус заказа:
     *  Created
     *
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    @Column(name = "SIGNATURE")
    val signature: String,

) : Serializable