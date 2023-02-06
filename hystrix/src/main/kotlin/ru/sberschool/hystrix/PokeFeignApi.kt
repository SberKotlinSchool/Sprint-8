package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine
import ru.sberschool.hystrix.dto.Item

interface PokeFeignApi {

    @RequestLine("GET /item/{itemId}")
    fun getItem(@Param(value = "itemId") itemId: Int) : Item

}


