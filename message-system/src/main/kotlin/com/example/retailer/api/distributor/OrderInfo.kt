package com.example.retailer.api.distributor

import javax.persistence.Entity
import javax.persistence.EnumType.ORDINAL
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.Id

/**
 * Уведомление об изменении заказа
 */
@Entity
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
    @Enumerated(ORDINAL)
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    val signature: String,

)