package com.example.retailer.adapter

import com.example.retailer.api.distributor.OrderInfo

/**
 * Интерфейс для получения обновлений от дистрибьютора
 */
interface RetailerConsumer {

    fun updateOrder(orderInfo: OrderInfo)
}