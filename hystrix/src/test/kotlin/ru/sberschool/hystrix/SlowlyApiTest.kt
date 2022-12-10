package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.integration.ClientAndServer
import ru.sberschool.hystrix.model.PokemonSpecieResult
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SlowlyApiTest {

    private val fallbackClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
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
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getPokemonSpecie() should return real data`() {

        val realResult  = fallbackClient.getPokemonSpecie()

        val expectedPokemonResult = PokemonSpecieResult("test", "http://test")
        assertEquals(1L, realResult.count)
        assertEquals(listOf(expectedPokemonResult), realResult.results)
        assertEquals("http://test", realResult.next)
        assertEquals("http://test", realResult.previous)
    }

    @Test
    fun `getPokemonSpecie() should return predefined data`() {
        val predefinedResult = realClient.getPokemonSpecie()

        assertEquals(905L, predefinedResult.count)
        assertEquals(20, predefinedResult.results.size)
        assertEquals("https://pokeapi.co/api/v2/pokemon-species/?offset=20&limit=20", predefinedResult.next)
        assertNull(predefinedResult.previous)
    }
}
