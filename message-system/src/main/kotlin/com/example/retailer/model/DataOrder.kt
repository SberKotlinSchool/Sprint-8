package com.example.retailer.model

import javax.persistence.*

/**
 * Описание заказа
 */
@Entity
class DataOrder(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    @GeneratedValue
    var id: Long? = null,
    /**
     * Произвольный адрес доставки
     */
    @Column
    var address: String? = null,

    /**
     * Произвольный получатель доставки
     */
    @Column
    var recipient: String? = null,

    /**
     * Список заказанных товаров
     */
    @OneToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    var items: List<DataItem> = mutableListOf()
)