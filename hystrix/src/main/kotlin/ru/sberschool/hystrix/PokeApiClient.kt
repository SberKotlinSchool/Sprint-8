package ru.sberschool.hystrix

import feign.Param
import feign.RequestLine
import ru.sberschool.hystrix.domain.Item

interface PokeApiClient {
  @RequestLine("GET /item/{id}")
  fun getItem(@Param("id") id: Int): Item
}