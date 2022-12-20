package com.example.retailer.mapper

import com.example.retailer.api.distributor.OrderInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class RetailMapper {
    private val mapper = jacksonObjectMapper()
    fun mapToJsonString(obj: Any?) = mapper.writeValueAsString(obj)
    fun mapToOrderInfo(msg: String) = mapper.readValue<OrderInfo>(msg)
}