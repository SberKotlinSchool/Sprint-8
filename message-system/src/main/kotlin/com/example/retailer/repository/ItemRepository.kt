package com.example.retailer.repository

import com.example.retailer.api.distributor.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface  ItemRepository : JpaRepository<Item, Long> {
}