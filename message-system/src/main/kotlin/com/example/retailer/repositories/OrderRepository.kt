package com.example.retailer.repositories

import com.example.retailer.api.distributor.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<Order, String>