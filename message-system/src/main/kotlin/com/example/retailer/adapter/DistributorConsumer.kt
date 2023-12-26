package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

/**
 * Интерфейс для получения обновлений статуса заказа от дистрибьютора
 */
interface DistributorConsumer {
    /**
     * Метод для получения обновлений статуса заказа от дистрибьютора
     */
    fun receiveUpdate(orderInfo: OrderInfo)
}