package com.example.retailer.api.distributor

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

/**
 * Уведомление об изменении заказа
 */
@Entity
@Table(name= "orders_info")
@kotlinx.serialization.Serializable
data class OrderInfo(
    @Id
    val orderId: String,
    @Column
    @Enumerated(EnumType.STRING)
    var status: OrderStatus,
    @Column val signature: String,
)
