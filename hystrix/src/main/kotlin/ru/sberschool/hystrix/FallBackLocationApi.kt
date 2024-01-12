package ru.sberschool.hystrix

class FallBackLocationApi : SlowlyApi{
    override fun getLocation(-1, "error");
}