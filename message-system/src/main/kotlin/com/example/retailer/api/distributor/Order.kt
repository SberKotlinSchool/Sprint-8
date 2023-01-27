package com.example.retailer.api.distributor

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.*

/**
 * Описание заказа
 */
@Entity
@Table(name = "ORDERS")
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    val id: String?,

    /**
     * Произвольный адрес доставки
     */
    @Column(name="ADDRESS")
    val address: String,

    /**
     * Произвольный получатель доставки
     */
    @Column(name="RECIPIENT")
    val recipient: String,

    /**
     * Список заказанных товаров
     */
    @OneToMany(cascade = [CascadeType.ALL])
    val items: List<Item>
): Serializable