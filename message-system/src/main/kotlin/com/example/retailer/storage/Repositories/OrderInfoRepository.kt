package com.example.retailer.storage.Repositories

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderInfoRepository : CrudRepository<OrderInfo, String> {
}