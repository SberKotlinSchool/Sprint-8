package com.example.retailer.api.distributor

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "order_info")
/**
 * Уведомление об изменении заказа
 */
data class OrderInfo(

    @Id
    /**
     * Уникальный идентификатор заказа
     *
     * @see com.example.retailer.api.distributor.Item#id
     */
    val orderId: String,

    @Enumerated(EnumType.STRING)
    /**
     * Статус заказа:
     *  Created
     *
     */
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    val signature: String,

)