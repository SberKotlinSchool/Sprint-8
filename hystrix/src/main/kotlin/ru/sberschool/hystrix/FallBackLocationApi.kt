package ru.sberschool.hystrix

import kotlin.reflect.jvm.internal.impl.incremental.components.LocationInfo

class FallBackLocationApi : LocationApi{
    override fun getLocation(id: Long) = Location();
}