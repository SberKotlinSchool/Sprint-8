package com.example.retailer.api.distributor

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

/**
 * Описание заказа
 */
@Entity
@Table(name = "orders")
data class Order(
    /**
     * Уникальный идентификатор заказа на стороне ретейлера
     */
    @Id
    @GeneratedValue(generator = "uuids")
    @GenericGenerator(name = "uuids", strategy = "uuid")
    val id: String?,

/**
@@ -22,5 +30,6 @@ data class Order(
/**
 * Список заказанных товаров
*/
@OneToMany(cascade = [CascadeType.ALL])
val items: List<Item>
)