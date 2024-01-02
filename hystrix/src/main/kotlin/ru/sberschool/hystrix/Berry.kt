package ru.sberschool.hystrix

data class BerryRequestParams(
    val offset: Int,
    val limit: Int
)
data class BerryPageableResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<BerryResult> = ArrayList()
)

data class BerryResult(
    val name: String? = null,
    val url: String? = null
)


