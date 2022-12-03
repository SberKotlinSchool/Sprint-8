package com.example.retailer.mapper

import com.example.retailer.api.distributor.Item
import com.example.retailer.model.DataItem
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ItemMapper {
    fun toEntity(dto: Item): DataItem
    fun toDto(entity: DataItem): Item
}