package com.example.retailer.api.distributor

import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.PrePersist
import javax.persistence.Table


@Entity
@Table(name = "orders")
/**
 * Описание заказа
 */
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    var id: String?,

    /**
     * Произвольный адрес доставки
     */
    val address: String,

    /**
     * Произвольный получатель доставки
     */
    val recipient: String,

    /**
     * Список заказанных товаров
     */
    @OneToMany(cascade = [CascadeType.ALL])
    val items: List<Item>
) {

    @PrePersist
    private fun ensureId() {
        id = UUID.randomUUID().toString().replace("-", "")
    }
}