package com.example.retailer.storage.Repositories

import com.example.retailer.api.distributor.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CrudRepository<Item, Long>