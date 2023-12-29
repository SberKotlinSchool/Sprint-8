package com.example.retailer.storage

import com.example.retailer.api.distributor.OrderStatus
import javax.persistence.*

@Entity
class OrderEntity (
        @Id
        var id: String,

        /**
         * Произвольный адрес доставки
         */
        var address: String,

        /**
         * Произвольный получатель доставки
         */
        var recipient: String,

        @Enumerated(value = EnumType.STRING)
        var orderStatus: OrderStatus,

        @OneToMany(mappedBy = "orderEntity", cascade = [CascadeType.ALL], targetEntity = ItemEntity::class, orphanRemoval = true)
        var itemList: MutableList<ItemEntity> = mutableListOf(),

)