package com.example.retailer.repository

import com.example.retailer.model.DataOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataOrderRepository: JpaRepository<DataOrder, Long>