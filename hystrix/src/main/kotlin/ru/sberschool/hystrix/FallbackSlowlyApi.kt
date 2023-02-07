package ru.sberschool.hystrix

import ru.sberschool.entity.Person

class FallbackSlowlyApi : SlowlyApi {
    override fun getPerson(personId: Int) = Person("Error in searching", "", "", "")
}


