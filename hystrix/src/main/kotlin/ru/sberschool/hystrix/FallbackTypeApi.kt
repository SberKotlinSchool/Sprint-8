package ru.sberschool.hystrix

class FallbackTypeApi : TypeApi {
    override fun getTypeById(typeId: Int) =
        Type(-1, "EmptyType", emptyList(), emptyList())
}


