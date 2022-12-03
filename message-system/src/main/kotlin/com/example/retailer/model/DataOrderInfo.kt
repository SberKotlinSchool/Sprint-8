package com.example.retailer.model

import com.example.retailer.api.distributor.OrderStatus
import javax.persistence.*

/**
 * Уведомление об изменении заказа
 */
@Entity
class DataOrderInfo(

    @Id
    @GeneratedValue
    var id: Long? = null,

    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    var order: DataOrder,

    @Column
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.CREATED,

    @Column
    var signature: String,

)

