package com.example.retailer.repositories

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.repository.CrudRepository

interface OrderInfoRepository: CrudRepository<OrderInfo, String>