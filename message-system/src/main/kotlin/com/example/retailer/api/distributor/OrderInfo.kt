package com.example.retailer.api.distributor

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.EnumType
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
    @JsonProperty("orderId")
    val orderId: String,

    /**
     * Статус заказа:
     *  Created
     *
     */

    @JsonProperty("status")
    @Enumerated(EnumType.ORDINAL)
    var status: OrderStatus,

    /**
     * Контрольная сумма
     */
    @JsonProperty("signature")
    val signature: String,

) : Serializable