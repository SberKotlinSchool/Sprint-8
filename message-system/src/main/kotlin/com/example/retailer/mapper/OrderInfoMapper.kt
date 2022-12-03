package com.example.retailer.mapper

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.model.DataOrderInfo
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [OrderMapper::class])
interface OrderInfoMapper {
    fun toEntity(dto: OrderInfo): DataOrderInfo
    @Mappings(
        Mapping(source = "order.id", target = "orderId")
    )
    fun toDto(entity: DataOrderInfo): OrderInfo
}