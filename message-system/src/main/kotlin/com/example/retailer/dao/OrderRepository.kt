package com.example.retailer.dao

import com.example.retailer.api.distributor.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
}