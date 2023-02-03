package com.example.retailer.repository

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.repository.CrudRepository

interface OrderInfoRepository : CrudRepository<OrderInfo, String>