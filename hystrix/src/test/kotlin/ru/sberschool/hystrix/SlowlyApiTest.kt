package ru.sberschool.hystrix

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import org.junit.jupiter.api.Test
import ru.sberschool.hystrix.api.FallbackSlowlyApi
import ru.sberschool.hystrix.api.SlowlyApi
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SlowlyApiTest {

    private val fallbackClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder(ObjectMapper().registerKotlinModule()))
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "http://127.0.0.1:18080", FallbackSlowlyApi())

    private val pokeApiClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder(ObjectMapper().registerKotlinModule()))
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(3, TimeUnit.SECONDS, 3, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "https://pokeapi.co/api/v2/", FallbackSlowlyApi())

    @Test
    fun `getSomething() should return available API methods`() {
        // given
        val responseMap = pokeApiClient.getAllMethods()

        // expect
        assertTrue(responseMap.keys.contains("pokemon"))
        assertTrue(responseMap.keys.contains("item"))
        assertEquals("https://pokeapi.co/api/v2/pokemon/", responseMap["pokemon"])
    }

    @Test
    fun `getItems() should return real data`() {
        // given
        val response = pokeApiClient.getItems()

        // expect
        assertTrue(response.results.isNotEmpty())
    }

    @Test
    fun `getItems() should return predefined data`() {
        // given
        val response = fallbackClient.getItems()

        // expect
        assertEquals(-1L, response.count)
    }
}
