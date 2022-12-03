package com.example.retailer.api.distributor

/**
 * Уведомление об изменении заказа
 */
data class OrderInfo(

    /**
     * Уникальный идентификатор заказа
     *
     * @see com.example.retailer.api.distributor.Item#id
     */
    var orderId: String = "",

    /**
     * Статус заказа:
     *  Created
     *
     */
    var status: OrderStatus = OrderStatus.CREATED,

    /**
     * Контрольная сумма
     */
    var signature: String = "",

)