package ru.sberschool.hystrix

import ru.sberschool.hystrix.domain.Item

class FallbackPokeApi: PokeApiClient {
  override fun getItem(id: Int) = Item(id = 12, name = "abc", cost = 10)
}