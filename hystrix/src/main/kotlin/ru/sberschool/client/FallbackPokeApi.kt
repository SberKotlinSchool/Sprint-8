package ru.sberschool.client

import ru.sberschool.client.domain.Berry

class FallbackPokeApi: PokeApiClient {
    override fun getBerry(id: Int) = Berry(1, "name", 1, 1, 1,
        1, 1, 1)
}