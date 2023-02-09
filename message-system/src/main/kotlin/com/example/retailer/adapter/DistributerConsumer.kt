package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

/**
 * Интерфейс для отправки заказа дистрибьютору
 */
interface DistributorConsumer {

    /**
     * Метод для получения уведомления об изменении заказа
     */
    fun getOrderUpdate(orderInfo: OrderInfo)
}