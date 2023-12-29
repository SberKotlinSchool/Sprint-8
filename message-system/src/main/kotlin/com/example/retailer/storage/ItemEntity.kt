package com.example.retailer.storage

import javax.persistence.*

@Entity
class ItemEntity(
        @Id
        @GeneratedValue
        var id: Long = 0,

        val name: String,

        @ManyToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "order_id", referencedColumnName = "id")
        var orderEntity: OrderEntity?,
)