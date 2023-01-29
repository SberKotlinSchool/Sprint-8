package com.example.retailer.api.distributor

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

/**
 * Описание заказа
 */
@Entity
@Table(name = "Orders")
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    val id: String?,

    /**
     * Произвольный адрес доставки
     */
    @Column
    val address: String,

    /**
     * Произвольный получатель доставки
     */
    @Column
    val recipient: String,

    /**
     * Список заказанных товаров
     */
    @OneToMany
    val items: List<Item>
) {
    override fun toString(): String {
        return "Order(id=$id, address='$address', recipient='$recipient', items=${items.map { it.id }.toList()})"
    }
}