package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getPonyta(): Ponyta = Ponyta(-1, "errorsaur")
}


