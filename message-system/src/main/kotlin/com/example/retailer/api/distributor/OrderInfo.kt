package com.example.retailer.api.distributor

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Уведомление об изменении заказа
 */
@Entity
@Table(name = "order_info")
data class OrderInfo(

    /**
     * Уникальный идентификатор заказа
     *
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
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    @Column
    val signature: String,

)