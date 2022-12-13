package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.integration.ClientAndServer
import ru.sberschool.hystrix.dto.Pokemon
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class SlowlyApiTest {
    private val fallbackClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "http://127.0.0.1:18080", FallbackSlowlyApi())

    private val realClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "https://pokeapi.co/api/v2", FallbackSlowlyApi())

    private lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getPokemon() returns real data`() {
        val result = realClient.getPokemon()

        assertEquals(35, result.id)
        assertEquals("clefairy", result.name)
        assertEquals(6, result.height)
        assertEquals(75, result.weight)
    }

    @Test
    fun `getPokemon() returns predefined data`() {
        val result = fallbackClient.getPokemon()

        assertEquals(Pokemon(-1, "Too long to wait"), result)
    }
}
