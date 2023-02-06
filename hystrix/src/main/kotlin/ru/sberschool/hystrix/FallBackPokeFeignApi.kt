package ru.sberschool.hystrix

import ru.sberschool.hystrix.dto.Item

class FallBackPokeFeignApi : PokeFeignApi {
    override fun getItem(itemId: Int) = Item(0, "Empty Item", 0)
}