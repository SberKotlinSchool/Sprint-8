package com.example.retailer.mapper

import com.example.retailer.api.distributor.Order
import com.example.retailer.model.DataOrder
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [ItemMapper::class])
interface OrderMapper {
    fun toEntity(dto: Order): DataOrder
    fun toDto(entity: DataOrder): Order
}