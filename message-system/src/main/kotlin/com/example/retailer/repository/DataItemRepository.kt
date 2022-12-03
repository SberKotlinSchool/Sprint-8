package com.example.retailer.repository

import com.example.retailer.model.DataItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataItemRepository : JpaRepository<DataItem, Long>
