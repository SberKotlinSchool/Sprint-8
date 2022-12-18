package ru.sberschool.hystrix.api

import ru.sberschool.hystrix.data.models.Item

class FallBackPokeFeignApi : PokeFeignApi {
    override fun getItem(itemId: Int) = Item(0,"Empty Item", 0)
}