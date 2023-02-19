package com.example.retailer.dao

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository

interface InfoRepository : JpaRepository<OrderInfo, String> {
}