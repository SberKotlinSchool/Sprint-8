package com.example.retailer.repo

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository

interface OrderInfoRepository : JpaRepository<OrderInfo, String>

interface OrderRepository : JpaRepository<Order, String>