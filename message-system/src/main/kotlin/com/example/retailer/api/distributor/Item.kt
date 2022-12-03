package com.example.retailer.api.distributor

/**
 * Описание товара
 */
data class Item(
    /**
     * Произвольный идентификатор
     */
    var id: Long,

    /**
     * Произвольное название
     */
    var name: String
)