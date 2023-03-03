package ru.sberschool.hystrix

import ru.sberschool.hystrix.model.Berry

class FallbackBerryApi : BerryApi {
    override fun getBerry(berryId: Int) = Berry(0,"No berry", 0, 0)
}