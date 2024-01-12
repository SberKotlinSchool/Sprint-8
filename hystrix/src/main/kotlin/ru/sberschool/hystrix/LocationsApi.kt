package ru.sberschool.hystrix

interface LocationsApi {
    @RequestLine("GET /api/v2/location/{id}")
    fun getLocation(@Param("id") id: Long): Location
}


