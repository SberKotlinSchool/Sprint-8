package ru.sberschool.hystrix

import kotlin.reflect.jvm.internal.impl.incremental.components.LocationInfo

class FallBackLocationApi : SlowlyApi{
    override fun getLocation(id: Long) = Location();
}