package com.example.retailer.storage

import com.example.retailer.api.distributor.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item, Long>{
}