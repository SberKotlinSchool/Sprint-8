package com.example.retailer.storage

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository

interface InformationRepository : JpaRepository<OrderInfo, String>