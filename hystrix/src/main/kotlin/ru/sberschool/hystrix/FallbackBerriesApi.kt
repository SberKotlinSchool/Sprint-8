package ru.sberschool.hystrix

class FallbackBerriesApi : BerriesApi {
    override fun getBerry(id: Long) = Berry()
}