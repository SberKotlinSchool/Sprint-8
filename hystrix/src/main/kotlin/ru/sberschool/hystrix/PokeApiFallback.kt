package ru.sberschool.hystrix

class PokeApiFallback : PokeApi {
    override fun getBerries(params: BerryRequestParams) = BerryPageableResponse(
        10,
        "nextPage",
        null,
        listOf(BerryResult("someBerry", "https://pokeapi.site/urlToBerry"))
    )
}
