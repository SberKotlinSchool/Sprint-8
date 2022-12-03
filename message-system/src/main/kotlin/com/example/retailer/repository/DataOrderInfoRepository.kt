package com.example.retailer.repository

import com.example.retailer.model.DataOrderInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DataOrderInfoRepository: JpaRepository<DataOrderInfo, Long>