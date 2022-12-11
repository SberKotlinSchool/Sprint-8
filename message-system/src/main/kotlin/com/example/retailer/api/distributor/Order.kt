package com.example.retailer.api.distributor

/**
 * Описание заказа
 */
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    var id: String? = null,

    /**
     * Произвольный адрес доставки
     */
    var address: String = "",

    /**
     * Произвольный получатель доставки
     */
    var recipient: String = "",

    /**
     * Список заказанных товаров
     */
    var items: List<Item> = mutableListOf()
)