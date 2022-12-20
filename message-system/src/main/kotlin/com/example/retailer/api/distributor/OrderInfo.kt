package com.example.retailer.api.distributor

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import java.io.Serializable

/**
 * Уведомление об изменении заказа
 */
@Entity
@Table(name = "otders_info")
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