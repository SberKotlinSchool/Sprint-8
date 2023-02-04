package ru.sberschool.hystrix

val DEFAULT = ContestTypeResponse(
    id = -1,
    name = "placeholder",
    berryFlavour = BerryFlavour(name = "placeholder", url = "placeholder"),
    names = emptyList()
)

class FallbackSlowlyApi : PokeContestApi {

    override fun getCoolContestType() = DEFAULT
}
