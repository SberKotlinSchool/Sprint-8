package ru.sberschool.hystrix.api

import feign.Param
import feign.RequestLine
import ru.sberschool.hystrix.data.models.Item


interface PokeFeignApi {

    @RequestLine("GET /item/{itemId}")
    fun getItem(@Param(value = "itemId") itemId: Int) : Item
}