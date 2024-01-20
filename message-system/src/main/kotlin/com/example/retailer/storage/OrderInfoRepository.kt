package com.example.retailer.storage

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderInfoRepository : JpaRepository<OrderInfo, String>